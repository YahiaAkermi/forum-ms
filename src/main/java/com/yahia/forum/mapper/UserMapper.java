package com.yahia.forum.mapper;

import com.yahia.forum.dto.UserDto;
import com.yahia.forum.entity.User;

public class UserMapper {

    public static UserDto mapToUserDTo(User user, UserDto userDto) {
        userDto.setUsername(user.getUsername());
        userDto.setEmail(user.getEmail());
        userDto.setUserType(user.getUserType());
        return userDto;
    }

    public static User mapToUser(UserDto userDto, User user) {
        user.setUsername(userDto.getUsername());
        user.setEmail(userDto.getEmail());
        user.setUserType(userDto.getUserType());
        return user;
    }

}
