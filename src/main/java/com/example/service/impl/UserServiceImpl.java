package com.example.service.impl;

import java.util.Optional;
import com.example.dto.UserDto;
import com.example.entity.User;
import com.example.dto.UserDtoUI;
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
    public UserDto createUser(UserDtoUI userDtoUI) {
        User user = UserMapper.toEntity(userDtoUI);
        userRepository.save(user);
        return UserMapper.toDto(user);
    }

    @Override
    public UserDto getUserById(Long id) {
        Optional<User> optional = userRepository.findById(id);
        if (optional.isEmpty()){
            throw new UserNotFoundException("User not found !!");
        }
        return UserMapper.toDto(optional.get());
    }

    @Override
    public UserDto updateUser(Long id, UserDtoUI userDtoUI) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found !!"));
        user.setUsername(userDtoUI.getUsername());
        user.setEmail(userDtoUI.getEmail());
        return UserMapper.toDto(userRepository.save(user));
    }
}
