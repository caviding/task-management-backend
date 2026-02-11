package com.example.mapper;

import com.example.dto.TaskDto;
import com.example.dto.TaskDtoUI;
import com.example.entity.Task;

public class TaskMapper {

    public static TaskDto toDto(Task task) {
        return new TaskDto(
                task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.getStatus()
        );
    }

    public static Task toEntity(TaskDtoUI taskDtoUI) {
        Task task = new Task();
        task.setTitle(taskDtoUI.getTitle());
        task.setDescription(taskDtoUI.getDescription());
        task.setStatus(taskDtoUI.getStatus());
        task.setUserId(taskDtoUI.getUserId());
        return task;
    }
}
