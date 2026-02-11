package com.example.controller.impl;

import com.example.dto.TaskDto;
import com.example.dto.TaskDtoUI;
import com.example.Enum.TaskStatus;
import com.example.dto.TaskUpdateDto;
import com.example.entity.RootEntity;
import com.example.service.ITaskService;
import org.springframework.data.domain.*;
import org.springframework.http.HttpStatus;
import com.example.controller.ITaskController;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.annotation.Validated;
import org.springframework.beans.factory.annotation.Autowired;


@RestController
@RequestMapping("/rest/api/task")
@Validated
    public class TaskController implements ITaskController {

    @Autowired
    private ITaskService taskService;

    @Override
    @PostMapping("/create")
    public RootEntity<TaskDto> createTask(@RequestBody TaskDtoUI taskDtoUI) {

        return RootEntity.success(taskService.createTask(taskDtoUI), HttpStatus.CREATED);
    }

    @Override
    @GetMapping("/{id}")
    public RootEntity<TaskDto> getTaskById(@PathVariable Long id) {
        return RootEntity.success(taskService.getTaskById(id),HttpStatus.OK);
    }

    @Override
    @DeleteMapping({"/delete","/delete/"})
    public RootEntity<Void> deleteTaskById(@RequestParam(name = "userId") Long user_id,
                                               @RequestParam(name = "taskId") Long task_id) {
        taskService.deleteTaskById(user_id,task_id);
        return RootEntity.success(null,HttpStatus.OK);
    }

    @Override
    @PutMapping({"/updateStatus/{id}","/updateStatus/{id}/"})
    public RootEntity<TaskDto> updateTaskStatus(@PathVariable Long id,
                                    @RequestParam(name = "status") TaskStatus status) {
        return RootEntity.success(taskService.updateTaskStatus(id,status),HttpStatus.OK);
    }

    @Override
    @PutMapping({"/update/{id}","/update/{id}/"})
    public RootEntity<TaskDto> updateTask(@PathVariable Long id,
                                              @RequestBody TaskUpdateDto taskUpdateDto) {
        return RootEntity.success(taskService.updateTask(id,taskUpdateDto),HttpStatus.OK);
    }

    @Override
    @GetMapping({"/list","/list/"})
    public RootEntity<Page<TaskDto>> getAllTasks(@RequestParam(name = "status",required = false) TaskStatus status,
                                                 @RequestParam(name = "pageNumber") int pageNumber,
                                                 @RequestParam(name = "pageSize") int pageSize,
                                                 @RequestParam(name = "sort",defaultValue = "id") String sort) {
        Sort.Order order = Sort.Order.asc(sort);
        Pageable pageable = PageRequest.of(pageNumber,pageSize, Sort.by(order));
        Page<TaskDto> page = taskService.getAllTasks(status,pageable);
        return RootEntity.success(page,HttpStatus.OK);
    }

    @Override
    @GetMapping({"/usertasks/{user_id}/","/usertasks/{user_id}"})
    public RootEntity<Page<TaskDto>> getTasksByUser(@PathVariable(name = "user_id") Long user_id,
                                                    @RequestParam(name = "status", required = false) TaskStatus status,
                                                    @RequestParam(name = "pageNumber") int pageNumber,
                                                    @RequestParam(name = "pageSize") int pageSize,
                                                    @RequestParam(name = "sort",defaultValue = "id") String sort) {
        Sort.Order order = Sort.Order.asc(sort);
        Pageable pageable = PageRequest.of(pageNumber,pageSize, Sort.by(order));
        Page<TaskDto> page = taskService.getTasksByUser(user_id,status,pageable);
        return RootEntity.success(page,HttpStatus.OK);
    }

    @Override
    @GetMapping({"/search","/search/"})
    public RootEntity<Page<TaskDto>> getTasks(@RequestParam(name = "user_id",required = false) Long user_id,
                                              @RequestParam(name = "status",required = false) TaskStatus status,
                                              @RequestParam(name = "title",required = false) String title,
                                              @RequestParam(name = "pageNumber",defaultValue = "0") int pageNumber,
                                              @RequestParam(name = "pageSize",defaultValue = "5") int pageSize,
                                              @RequestParam(name = "sort",required = false,defaultValue = "id") String sort) {
        Pageable pageable = PageRequest.of(pageNumber,pageSize, Sort.by(sort));
        Page<TaskDto> page = taskService.getTasks(user_id,status,title,pageable);
        return RootEntity.success(page,HttpStatus.OK);
    }


}
