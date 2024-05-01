package com.yahia.forum.controller;

import com.yahia.forum.constants.PostConstants;

import com.yahia.forum.dto.PostsDto;
import com.yahia.forum.dto.ResponseDto;

import com.yahia.forum.entity.enums.UserType;
import com.yahia.forum.service.IPostsService;
import lombok.AllArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

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

    @GetMapping("/fetch")
    public ResponseEntity<Collection<PostsDto>> fetchAllPosts(){

        Collection<PostsDto> allposts=iPostsService.fetchAllPosts();

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(allposts);

    }

    @GetMapping("/fetch-with-filter")
    public ResponseEntity<Collection<PostsDto>> fetchPostsByUserType(
            @RequestParam UserType userType
    ){

        Collection<PostsDto> filteredPosts=iPostsService.fetchPostsByUserType(userType);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(filteredPosts);

    }

}
