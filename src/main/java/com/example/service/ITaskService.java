package com.example.service;

import com.example.dto.TaskDto;
import com.example.dto.TaskDtoUI;
import com.example.Enum.TaskStatus;
import com.example.dto.TaskUpdateDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

public interface ITaskService {
     TaskDto createTask(TaskDtoUI taskDtoUI);
     TaskDto getTaskById(Long id);
     void deleteTaskById(Long user_id, Long task_id);
     TaskDto updateTaskStatus(Long id, TaskStatus status);
     TaskDto updateTask(Long id, TaskUpdateDto taskUpdateDto);
     Page<TaskDto> getAllTasks(TaskStatus status, Pageable pageable);
     Page<TaskDto> getTasksByUser(Long user_id, TaskStatus status, Pageable pageable);
     Page<TaskDto> getTasks(Long user_id, TaskStatus status, String title, Pageable pageable);
}
