package com.example.mapper;

import com.example.dto.TaskDto;
import com.example.dto.UserDto;
import com.example.dto.UserDtoUI;
import com.example.entity.Task;
import com.example.entity.User;

import java.util.ArrayList;
import java.util.List;

public class UserMapper {

    public static User toEntity(UserDtoUI userDtoUI){
        User user = new User();
        user.setUsername(userDtoUI.getUsername());
        user.setEmail(userDtoUI.getEmail());
        return user;
    }

    public static UserDto toDto(User user){
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setUsername(user.getUsername());
        userDto.setEmail(user.getEmail());
        List<TaskDto> taskDto = new ArrayList<>();
        for (Task t : user.getTasks()){
            taskDto.add(TaskMapper.toDto(t));
        }
        userDto.setTasks(taskDto);
        return userDto;
    }


}
