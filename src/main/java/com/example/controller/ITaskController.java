package com.example.controller;

import com.example.dto.TaskDto;
import jakarta.validation.Valid;
import com.example.dto.TaskDtoUI;
import com.example.Enum.TaskStatus;
import com.example.entity.RootEntity;
import com.example.dto.TaskUpdateDto;
import jakarta.validation.constraints.Min;
import org.springframework.data.domain.Page;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.NotEmpty;

public interface ITaskController {
    RootEntity<TaskDto> createTask(@Valid TaskDtoUI taskDtoUI);
    RootEntity<TaskDto> getTaskById(@Positive(message = "Id cannot be negative !!") Long id);
    RootEntity<Void> deleteTaskById(@Positive(message = "UserId cannot be negative !!") Long user_id,
                                    @Positive(message = "TaskId cannot be negative !!") Long task_id);
    RootEntity<TaskDto> updateTaskStatus(@Positive(message = "Id cannot be negative !!") Long id,
                                         @NotEmpty(message = "Status cannot be empty !!") TaskStatus status);
    RootEntity<TaskDto> updateTask(@Positive(message = "Id cannot be negative !!") Long id,@Valid TaskUpdateDto taskUpdateDto);
    RootEntity<Page<TaskDto>> getAllTasks(TaskStatus status,
                                          @Min(value = 0,message = "Page number cannot be negative !!") int pageNumber,
                                          @Min(value = 1,message = "Page size cannot be lower than 1 !!") int pageSize, String sort);
    RootEntity<Page<TaskDto>> getTasksByUser(@Positive(message = "Id cannot be negative !!") Long user_id, TaskStatus status,
                                             @Min(value = 0,message = "Page number cannot be negative !!") int pageNumber,
                                             @Min(value = 1,message = "Page size cannot be lower than 1 !!")int pageSize, String sort);
    RootEntity<Page<TaskDto>> getTasks(@Positive(message = "Id cannot be negative !!") Long user_id, TaskStatus status, String title,
                                       @Min(value = 0,message = "Page number cannot be negative !!") int pageNumber,
                                       @Min(value = 1,message = "Page size cannot be lower than 1 !!") int pageSize, String sort);

}
