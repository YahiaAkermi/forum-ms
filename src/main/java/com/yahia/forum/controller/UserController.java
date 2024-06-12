package com.yahia.forum.controller;

import com.yahia.forum.constants.UserConstants;
import com.yahia.forum.dto.ResponseDto;
import com.yahia.forum.dto.UserDto;

import com.yahia.forum.service.IUserService;

import lombok.AllArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Email;

@RestController
@RequestMapping("/postcreator")
@AllArgsConstructor @Validated
public class UserController {

    private IUserService iUserService;

    @PostMapping("/create")
    public ResponseEntity<ResponseDto> createUser(@Valid @RequestBody UserDto userDto){

        //add the logic
        iUserService.createUser(userDto);


        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ResponseDto(UserConstants.STATUS_201,UserConstants.MESSAGE_201));
    }

    @GetMapping("/fetch")
    public ResponseEntity<UserDto> fetchUser(
            @Email(message = "you should enter a valid email please")
            @RequestParam String email){

        UserDto userDto=iUserService.fetchUser(email);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(userDto);

    }

    @PutMapping("/update")
    public ResponseEntity<ResponseDto> updateUser(@Valid @RequestBody UserDto userDto){

        boolean isUpdated= iUserService.updateUser(userDto);

        if(isUpdated){
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new ResponseDto(UserConstants.STATUS_200,UserConstants.MESSAGE_200));
        }else{
            return ResponseEntity
                    .status(HttpStatus.EXPECTATION_FAILED)
                    .body(new ResponseDto(UserConstants.STATUS_417,UserConstants.MESSAGE_417_UPDATE));
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<ResponseDto> deleteUser(
            @Email(message = "you should enter a valid email please")
            String email){

        boolean isDeleted= iUserService.deleteUser(email);

        if(isDeleted){
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new ResponseDto(UserConstants.STATUS_200,UserConstants.MESSAGE_200));
        }else{
            return ResponseEntity
                    .status(HttpStatus.EXPECTATION_FAILED)
                    .body(new ResponseDto(UserConstants.STATUS_417,UserConstants.MESSAGE_417_DELETE));
        }
    }



}
