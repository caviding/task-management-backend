package com.example.service.impl;

import java.util.List;
import com.example.entity.User;
import com.example.entity.Task;
import com.example.dto.TaskDto;
import com.example.dto.TaskDtoUI;
import com.example.Enum.TaskStatus;
import com.example.dto.TaskUpdateDto;
import com.example.mapper.TaskMapper;
import com.example.service.ITaskService;
import org.springframework.data.domain.Page;
import com.example.repository.UserRepository;
import com.example.repository.TaskRepository;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;
import com.example.exception.UserNotFoundException;
import com.example.exception.TaskNotFoundException;
import com.example.specifications.TaskSpecification;
import com.example.exception.TaskUserMismatchException;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.beans.factory.annotation.Autowired;


@Service
public class TaskServiceImpl implements ITaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public TaskDto createTask(TaskDtoUI taskDtoUI) {
        if (!userRepository.existsById(taskDtoUI.getUserId())){
            throw new UserNotFoundException("User not found !!");
        }else {
            Task task = TaskMapper.toEntity(taskDtoUI);
            List<Task> taskList = userRepository.findById(taskDtoUI.getUserId()).get().getTasks();
            taskList.add(task);
            userRepository.findById(taskDtoUI.getUserId()).get().setTasks(taskList);
            taskRepository.save(task);
            return TaskMapper.toDto(task);
        }
    }

    @Override
    public TaskDto getTaskById(Long id) {
        return TaskMapper.toDto(taskRepository.findById(id).get());
    }

    @Override
    public void deleteTaskById(Long user_id,Long task_id) {
        User user = userRepository.findById(user_id)
                .orElseThrow(() -> new UserNotFoundException("User not found !!"));

        Task task = taskRepository.findById(task_id)
                .orElseThrow(() -> new TaskNotFoundException("Task not found !!"));

        if (!user.getTasks().contains(task)) {
            throw new TaskUserMismatchException("This task is not assigned to this user !!");
        }
        else {
            List<Task> taskList = userRepository.findById(user_id).get().getTasks();
            taskList.remove(taskRepository.findById(task_id).get());
            userRepository.findById(user_id).get().setTasks(taskList);
            taskRepository.deleteById(task_id);
        }
    }

    @Override
    public TaskDto updateTaskStatus(Long id, TaskStatus status) {
        if (!taskRepository.existsById(id)){
            throw new TaskNotFoundException("Task not found !!");
        }
        Task task = taskRepository.findById(id).get();
        task.setStatus(status);
        taskRepository.save(task);
        return TaskMapper.toDto(task);
    }

    @Override
    public TaskDto updateTask(Long id, TaskUpdateDto taskUpdateDto) {
        if (!taskRepository.existsById(id)){
            throw new TaskNotFoundException("Task not found !!");
        }
        Task task = taskRepository.findById(id).get();
        task.setTitle(taskUpdateDto.getTitle());
        task.setDescription(taskUpdateDto.getDescription());
        task.setStatus(taskUpdateDto.getStatus());
        taskRepository.save(task);
        return TaskMapper.toDto(task);
    }

    @Override
    public Page<TaskDto> getAllTasks(TaskStatus status, Pageable pageable) {
        if (status == null){
            return taskRepository.findAll(pageable).map(TaskMapper::toDto);
        }
        return taskRepository.findByStatus(status,pageable).map(TaskMapper::toDto);

    }

    @Override
    public Page<TaskDto> getTasksByUser(Long user_id, TaskStatus status, Pageable pageable) {
        userRepository.findById(user_id)
                .orElseThrow(() -> new UserNotFoundException("User not found !!"));
        return taskRepository.findByUserIdAndStatus(user_id,status,pageable).map(TaskMapper::toDto);
    }

    @Override
    public Page<TaskDto> getTasks(Long user_id, TaskStatus status, String title, Pageable pageable) {

        if (user_id != null){
            userRepository.findById(user_id)
                    .orElseThrow(() -> new UserNotFoundException("User not found !!"));
        }
        Specification<Task> spec = null;
        if (user_id != null){
            spec = TaskSpecification.hasUserId(user_id);
        }
        if (status != null){
            spec = TaskSpecification.hasStatus(status);
        }
        if (title != null){
            spec = TaskSpecification.hasTitle(title);
        }
        if (spec == null){
            return taskRepository.findAll(pageable).map(TaskMapper::toDto);
        }
        return taskRepository.findAll(spec,pageable).map(TaskMapper::toDto);
    }
}
