package com.yahia.forum.controller;

import com.yahia.forum.constants.PostConstants;
import com.yahia.forum.constants.ReplyConstants;
import com.yahia.forum.dto.PostWithRepliesDto;
import com.yahia.forum.dto.PostsDto;
import com.yahia.forum.dto.ReplyDto;
import com.yahia.forum.dto.ResponseDto;
import com.yahia.forum.service.IPostsService;
import com.yahia.forum.service.IReplyService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

}
