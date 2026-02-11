package com.example.service;

import com.example.dto.UserDto;
import com.example.dto.UserDtoUI;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;

public interface IUserService {
     UserDto createUser(UserDtoUI userDtoUI);
     UserDto getUserById(Long id);
     UserDto updateUser(Long id,UserDtoUI userDtoUI);
}
