package com.yahia.forum.dto;

import com.yahia.forum.entity.enums.UserType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserDto {

    @NotEmpty(message = "username cannot be empty or null value")
    @Size(min = 3 ,max = 20,message = "username should be between 3 to 20 character")
    private String username;



    @Email(message = "you should enter a valid email please")
    private String email;


    private String idUserGroup;
}
