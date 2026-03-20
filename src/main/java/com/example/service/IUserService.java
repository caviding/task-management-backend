package com.example.service;

import com.example.dto.response.UserResponseDto;
import com.example.dto.request.UserRequestDto;

public interface IUserService {
     UserResponseDto createUser(UserRequestDto userRequestDto);
     UserResponseDto getUserById(Long id);
     UserResponseDto updateUser(Long id, UserRequestDto userRequestDto);
}
