package com.yahia.forum.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class UserAlreadyExistsExeption extends RuntimeException{

    public UserAlreadyExistsExeption(String msg){
        super(msg);
    }
}
