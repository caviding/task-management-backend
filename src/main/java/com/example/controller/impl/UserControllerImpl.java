package com.example.controller.impl;

import com.example.dto.UserDto;
import com.example.dto.UserDtoUI;
import com.example.entity.RootEntity;
import com.example.service.IUserService;
import org.springframework.http.HttpStatus;
import com.example.controller.IUserController;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;


@RestController
@RequestMapping("rest/api/user")
public class UserControllerImpl implements IUserController {

    @Autowired
    private IUserService userService;

    @Override
    @PostMapping("/create")
    public RootEntity<UserDto> createUser(@RequestBody UserDtoUI userDtoUI) {
        return RootEntity.success(userService.createUser(userDtoUI), HttpStatus.CREATED);
    }

    @Override
    @GetMapping("/{id}")
    public RootEntity<UserDto> getUserById(@PathVariable Long id) {
        return RootEntity.success(userService.getUserById(id),HttpStatus.OK);
    }

    @Override
    @PutMapping({"/update/{id}","/update/{id}/"})
    public RootEntity<UserDto> updateUser(@PathVariable Long id,
                                          @RequestBody UserDtoUI userDtoUI) {
        return RootEntity.success(userService.updateUser(id,userDtoUI),HttpStatus.OK);
    }
}
