package com.yahia.forum.controller;

import com.yahia.forum.constants.PostConstants;

import com.yahia.forum.dto.PostsDto;
import com.yahia.forum.dto.PostsDtoWithId;
import com.yahia.forum.dto.ResponseDto;

import com.yahia.forum.entity.enums.UserType;
import com.yahia.forum.model.UserAuth;
import com.yahia.forum.proxy.AuthProxy;
import com.yahia.forum.service.IPostsService;
import lombok.AllArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Pattern;
import java.util.Collection;

@RestController
@RequestMapping("/posts-api")
@AllArgsConstructor @Validated
public class PostsController {


    private IPostsService iPostsService;

    private AuthProxy authProxy;

    @PostMapping("/create")
    public ResponseEntity<ResponseDto> createPost(
            @Valid
            @RequestBody PostsDto postDto,@RequestParam String email){

        UserAuth userAuth = authProxy.getAuth(email,"toforum");


        iPostsService.createPost(postDto,userAuth);



        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ResponseDto(PostConstants.STATUS_201,PostConstants.MESSAGE_201));
    }

    @GetMapping("/fetch")
    public ResponseEntity<Collection<PostsDtoWithId>> fetchAllPosts(){

        Collection<PostsDtoWithId> allposts=iPostsService.fetchAllPosts();

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(allposts);

    }

    @GetMapping("/fetch-with-filter-userType")
    public ResponseEntity<Collection<PostsDtoWithId>> fetchPostsByUserType(
            @RequestParam UserType userType
    ){

        Collection<PostsDtoWithId> filteredPosts=iPostsService.fetchPostsByUserType(userType);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(filteredPosts);

    }

    @GetMapping("/fetch-with-filter-post-title")
    public ResponseEntity<Collection<PostsDtoWithId>> fetchPostsByTitle(
            @RequestParam String postTitle
    ){

        Collection<PostsDtoWithId> filteredPosts=iPostsService.fetchPostsByTitle(postTitle);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(filteredPosts);

    }


    @PutMapping("/update")
    public ResponseEntity<ResponseDto> updatePost(
            @Valid
            @RequestBody PostsDtoWithId postDtoWithId){

        boolean isUpdated=iPostsService.updatePost(postDtoWithId);

        if(isUpdated){
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new ResponseDto(PostConstants.STATUS_200,PostConstants.MESSAGE_200));
        }else{
            return ResponseEntity
                    .status(HttpStatus.EXPECTATION_FAILED)
                    .body(new ResponseDto(PostConstants.STATUS_417,PostConstants.MESSAGE_417_UPDATE));
        }

    }

    @DeleteMapping("/delete")
    public ResponseEntity<ResponseDto> deleteAccount(
            @Pattern(regexp = "(^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$)",message = "postId isn't in UUID 32bit format please enter the right one")
            @RequestParam String postId){

        boolean isDeleted= iPostsService.deletePost(postId);

        if(isDeleted){
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new ResponseDto(PostConstants.STATUS_200,PostConstants.MESSAGE_200));
        }else{
            return ResponseEntity
                    .status(HttpStatus.EXPECTATION_FAILED)
                    .body(new ResponseDto(PostConstants.STATUS_417,PostConstants.MESSAGE_417_DELETE));
        }

    }



}
