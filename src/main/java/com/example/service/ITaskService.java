package com.example.service;

import com.example.dto.response.TaskResponseDto;
import com.example.dto.request.TaskRequestDto;
import com.example.Enum.TaskStatus;
import com.example.dto.request.TaskUpdateDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ITaskService {
     TaskResponseDto createTask(TaskRequestDto taskRequestDto);
     TaskResponseDto getTaskById(Long id);
     void deleteTaskById(Long id);
     boolean isOwner(String username,Long id);
     TaskResponseDto updateTaskStatus(Long id, TaskStatus status);
     TaskResponseDto updateTask(Long id, TaskUpdateDto taskUpdateDto);
     Page<TaskResponseDto> getAllTasks(TaskStatus status, Pageable pageable);
     Page<TaskResponseDto> getTasksByUser(Long user_id, TaskStatus status, Pageable pageable);
     Page<TaskResponseDto> getTasks(Long user_id, TaskStatus status, String title, Pageable pageable);
}
