package com.example.controller;

import jakarta.validation.Valid;
import com.example.entity.RootEntity;
import com.example.dto.response.UserResponseDto;
import com.example.service.IUserService;
import com.example.dto.request.UserRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import jakarta.validation.constraints.Positive;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

@RestController
@RequestMapping("api/v1/user")
@RequiredArgsConstructor
public class UserController {

    private final IUserService userService;

    @PostMapping("/create")
    public RootEntity<UserResponseDto> createUser(@RequestBody @Valid UserRequestDto userRequestDto) {
        return RootEntity.success(userService.createUser(userRequestDto), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public RootEntity<UserResponseDto> getUserById(@PathVariable @Positive(message = "Id cannot be negative !1") Long id) {
        return RootEntity.success(userService.getUserById(id),HttpStatus.OK);
    }

    @PutMapping({"/update/{id}","/update/{id}/"})
    public RootEntity<UserResponseDto> updateUser(@PathVariable @Positive(message = "Id cannot be negative !1") Long id,
                                                  @RequestBody @Valid UserRequestDto userRequestDto) {
        return RootEntity.success(userService.updateUser(id, userRequestDto),HttpStatus.OK);
    }
}
