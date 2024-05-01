package com.yahia.forum.controller;

import com.yahia.forum.constants.PostConstants;
import com.yahia.forum.constants.UserConstants;
import com.yahia.forum.dto.PostsDto;
import com.yahia.forum.dto.ResponseDto;
import com.yahia.forum.service.IPostsService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/posts-api")
@AllArgsConstructor
public class PostsController {


    private IPostsService iPostsService;

    @PostMapping("/create")
    public ResponseEntity<ResponseDto> createPost(@RequestBody PostsDto postDto){

        iPostsService.createPost(postDto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ResponseDto(PostConstants.STATUS_201,PostConstants.MESSAGE_201));
    }

}
