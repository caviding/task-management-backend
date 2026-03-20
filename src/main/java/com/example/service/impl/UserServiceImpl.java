package com.example.service.impl;

import java.util.Optional;
import com.example.dto.response.UserResponseDto;
import com.example.entity.User;
import com.example.dto.request.UserRequestDto;
import com.example.mapper.UserMapper;
import com.example.service.IUserService;
import com.example.repository.UserRepository;
import org.springframework.stereotype.Service;
import com.example.exception.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;


@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserResponseDto createUser(UserRequestDto userRequestDto) {
        User user = UserMapper.toEntity(userRequestDto);
        userRepository.save(user);
        return UserMapper.toDto(user);
    }

    @Override
    public UserResponseDto getUserById(Long id) {
        Optional<User> optional = userRepository.findById(id);
        if (optional.isEmpty()){
            throw new UserNotFoundException("User not found !!");
        }
        return UserMapper.toDto(optional.get());
    }

    @Override
    public UserResponseDto updateUser(Long id, UserRequestDto userRequestDto) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found !!"));
        user.setUsername(userRequestDto.getUsername());
        user.setEmail(userRequestDto.getEmail());
        return UserMapper.toDto(userRepository.save(user));
    }
}
