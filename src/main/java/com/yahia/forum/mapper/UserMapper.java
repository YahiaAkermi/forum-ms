package com.yahia.forum.mapper;

import com.yahia.forum.dto.UserDto;
import com.yahia.forum.entity.User;

import java.util.Optional;

public class UserMapper {

    public static UserDto mapToUserDTo(User user, UserDto userDto) {
        userDto.setUsername(user.getUsername());
        userDto.setEmail(user.getEmail());
        userDto.setUserType(user.getUserType());
        return userDto;
    }

    public static UserDto mapToUserDTo2(Optional<User> user, UserDto userDto) {
        userDto.setUsername(user.get().getUsername());
        userDto.setEmail(user.get().getEmail());
        userDto.setUserType(user.get().getUserType());
        return userDto;
    }

    public static User mapToUser(UserDto userDto, User user) {
        user.setUsername(userDto.getUsername());
        user.setEmail(userDto.getEmail());
        user.setUserType(userDto.getUserType());
        return user;
    }

}
