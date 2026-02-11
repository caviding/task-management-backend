package com.example.controller;

import com.example.dto.UserDto;
import jakarta.validation.Valid;
import com.example.dto.UserDtoUI;
import com.example.entity.RootEntity;
import jakarta.validation.constraints.Positive;


public interface IUserController {
     RootEntity<UserDto> createUser(@Valid UserDtoUI userDtoUI);
     RootEntity<UserDto> getUserById(@Positive(message = "Id cannot be negative !1") Long id);
     RootEntity<UserDto> updateUser(@Positive(message = "Id cannot be negative !1") Long id, @Valid UserDtoUI userDtoUI);
}
