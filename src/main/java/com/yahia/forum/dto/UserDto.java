package com.yahia.forum.dto;

import com.yahia.forum.entity.enums.UserType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class UserDto {


    @Pattern(regexp = "(^[a-zA-Z][a-zA-Z0-9_-]{2,19}$)",message = "the username must begin with characters and withing 3 to 20 character")
    private String username;


    @Email(message = "you should enter a valid email please")
    private String email;

    @Pattern(regexp ="(^(student|teacher|admin)$)",message = "the user Type should be either student or teacher or admin (case sensitive)")
    private UserType userType;
}
