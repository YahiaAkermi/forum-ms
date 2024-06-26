package com.yahia.forum.controller;


import com.yahia.forum.constants.ReplyConstants;
import com.yahia.forum.dto.*;

import com.yahia.forum.service.IReplyService;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/reply-api")
@AllArgsConstructor
public class ReplyController {

    private IReplyService iReplyService;

    @PostMapping("/create")
    public ResponseEntity<ResponseDto> createPost(
            @RequestBody ReplyDto replyDto){

        iReplyService.createReply(replyDto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ResponseDto(ReplyConstants.STATUS_201,ReplyConstants.MESSAGE_201));
    }


    //we call this fetch when we want to see comments of a reply or when we go to post page details
    @GetMapping("/fetch-post-with-replies")
    public ResponseEntity<PostWithRepliesDto> fetchPostWithItsReplies(
            @RequestParam
            String postId){

       PostWithRepliesDto postWithRepliesDto= iReplyService.fetchPostReplies(postId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(postWithRepliesDto);
    }

    @GetMapping("/fetch-all-posts-with-their-replies")
    public ResponseEntity<Collection<PostWithRepliesDto>> fetchAllPostsWithTheirReplies(){

        Collection<PostWithRepliesDto> postWithRepliesDto= iReplyService.fetchAllPostsWithTheirReplies();

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(postWithRepliesDto);
    }

    @GetMapping("/fetch-all-posts-filter-by-content")
    public ResponseEntity<Collection<PostWithRepliesDto>> fetchAllPostsfiliteredByContent(@RequestParam String content,@RequestParam String idGroup){

        Collection<PostWithRepliesDto> postWithRepliesDto= iReplyService.filterPostsByContent(content,idGroup);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(postWithRepliesDto);
    }

    @GetMapping("/fetch-replies-of-user-in-particular-post")
    public ResponseEntity<PostWithRepliesDto> fetchRepliesOfParticularUserInASinglePost(
            @RequestParam String replierUsername,@RequestParam String postTitle){

        PostWithRepliesDto postWithRepliesDto= iReplyService.fetchRepliesOfParticularUserInASinglePost(replierUsername,postTitle);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(postWithRepliesDto);
    }

    @GetMapping("/fetch-all-posts-of-particular-group")
    public ResponseEntity<Collection<PostWithRepliesDto>> fetchPostsOfParticularGroup(
            @RequestParam String idGroup){

        Collection<PostWithRepliesDto> postWithRepliesDto= iReplyService.filterPostsByGroupId(idGroup);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(postWithRepliesDto);
    }

    @GetMapping("/fetch-all-groups-of-particular-user")
    public ResponseEntity<Collection<Collection<PostWithRepliesDto>>> fetchGroupOfParticularUser(
            @RequestParam String email){

        Collection<Collection<PostWithRepliesDto>> postWithRepliesDto= iReplyService.fetchGroupsOfParticularUser(email);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(postWithRepliesDto);
    }

    @PutMapping("/update")
    public ResponseEntity<ResponseDto> updateAccountDetails(
            @RequestBody ReplyWithIdtDto replyWithIdtDto){

        boolean isUpdated= iReplyService.updateReply(replyWithIdtDto);

        if(isUpdated){
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new ResponseDto(ReplyConstants.STATUS_200,ReplyConstants.MESSAGE_200));
        }else{
            return ResponseEntity
                    .status(HttpStatus.EXPECTATION_FAILED)
                    .body(new ResponseDto(ReplyConstants.STATUS_417,ReplyConstants.MESSAGE_417_UPDATE));
        }

    }

    @DeleteMapping("/delete")
    public ResponseEntity<ResponseDto> deleteAccount(@RequestParam String replyId){

        boolean isDeleted= iReplyService.deleteReply(replyId);

        if(isDeleted){
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new ResponseDto(ReplyConstants.STATUS_200,ReplyConstants.MESSAGE_200));
        }else{
            return ResponseEntity
                    .status(HttpStatus.EXPECTATION_FAILED)
                    .body(new ResponseDto(ReplyConstants.STATUS_417,ReplyConstants.MESSAGE_417_DELETE));
        }

    }

}
