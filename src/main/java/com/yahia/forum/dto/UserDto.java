package com.yahia.forum.dto;

import com.yahia.forum.entity.enums.UserType;
import lombok.Data;

@Data
public class UserDto {

    //lazm username ykoun unique

    private String username;

    //lazm email ykoun unique
    private String email;

    private UserType userType;
}
