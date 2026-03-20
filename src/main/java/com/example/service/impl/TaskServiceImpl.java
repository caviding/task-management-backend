package com.example.service.impl;

import java.util.List;
import com.example.entity.User;
import com.example.entity.Task;
import com.example.Enum.TaskStatus;
import com.example.mapper.TaskMapper;
import com.example.service.ITaskService;
import com.example.dto.response.TaskResponseDto;
import com.example.dto.request.TaskRequestDto;
import org.springframework.data.domain.Page;
import com.example.dto.request.TaskUpdateDto;
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
    public TaskResponseDto createTask(TaskRequestDto taskRequestDto) {
        if (!userRepository.existsById(taskRequestDto.getUserId())){
            throw new UserNotFoundException("User not found !!");
        }else {
            Task task = TaskMapper.toEntity(taskRequestDto);
            List<Task> taskList = userRepository.findById(taskRequestDto.getUserId()).get().getTasks();
            taskList.add(task);
            userRepository.findById(taskRequestDto.getUserId()).get().setTasks(taskList);
            taskRepository.save(task);
            return TaskMapper.toDto(task);
        }
    }

    @Override
    public TaskResponseDto getTaskById(Long id) {
        if (!taskRepository.existsById(id)){
            throw new TaskNotFoundException("Task not found !!");
        }
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
    public TaskResponseDto updateTaskStatus(Long id, TaskStatus status) {
        if (!taskRepository.existsById(id)){
            throw new TaskNotFoundException("Task not found !!");
        }
        Task task = taskRepository.findById(id).get();
        task.setStatus(status);
        taskRepository.save(task);
        return TaskMapper.toDto(task);
    }

    @Override
    public TaskResponseDto updateTask(Long id, TaskUpdateDto taskUpdateDto) {
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
    public Page<TaskResponseDto> getAllTasks(TaskStatus status, Pageable pageable) {
        if (status == null){
            return taskRepository.findAll(pageable).map(TaskMapper::toDto);
        }
        return taskRepository.findByStatus(status,pageable).map(TaskMapper::toDto);

    }

    @Override
    public Page<TaskResponseDto> getTasksByUser(Long user_id, TaskStatus status, Pageable pageable) {
        userRepository.findById(user_id)
                .orElseThrow(() -> new UserNotFoundException("User not found !!"));
        if (status == null){
            return taskRepository.findByUserId(user_id,pageable).map(TaskMapper::toDto);
        }
        return taskRepository.findByUserIdAndStatus(user_id,status,pageable).map(TaskMapper::toDto);
    }

    @Override
    public Page<TaskResponseDto> getTasks(Long user_id, TaskStatus status, String title, Pageable pageable) {

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
