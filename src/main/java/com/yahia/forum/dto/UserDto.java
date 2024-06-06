package com.yahia.forum.dto;

import com.yahia.forum.entity.enums.UserType;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
public class UserDto {

    @NotEmpty(message = "username cannot be empty or null value")
    @Size(min = 3 ,max = 20,message = "username should be between 3 to 20 character")
    private String username;



    @Email(message = "you should enter a valid email please")
    private String email;


    private UserType userType;
}
