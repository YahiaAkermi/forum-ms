package com.yahia.forum.service.impl;

import com.yahia.forum.dto.ReplyDto;
import com.yahia.forum.entity.Posts;
import com.yahia.forum.entity.Reply;
import com.yahia.forum.entity.User;
import com.yahia.forum.exception.ResourceNotFoundException;
import com.yahia.forum.mapper.ReplyMapper;
import com.yahia.forum.repository.PostsRepository;
import com.yahia.forum.repository.ReplyRepository;
import com.yahia.forum.repository.UserRepository;
import com.yahia.forum.service.IReplyService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service @AllArgsConstructor
public class ReplyServiceImpl implements IReplyService {

    private UserRepository userRepository;
    private PostsRepository postsRepository;
    private ReplyRepository replyRepository;

    /**
     * this method will create reply
     *
     * @param replyDto -  ReplyDto  object
     */
    @Override
    public void createReply(ReplyDto replyDto) {

        //checking if the post exists
        Posts retrievedPost=postsRepository.findByPostId(replyDto.getPostId()).orElseThrow(
                () -> new ResourceNotFoundException("Post","post ID", replyDto.getPostId())
        );

        //checking if the username exists
        User retrievedUser=userRepository.findUserByUsernameContains(replyDto.getUsername()).orElseThrow(
                () -> new ResourceNotFoundException("User","username", replyDto.getUsername())
        );

        //create id for reply
        String replyId= UUID.randomUUID().toString();

        //mapping to reply ,so I can store it in db
        Reply newReply= ReplyMapper.mapToReply(replyDto,new Reply());
        newReply.setReplyId(replyId);
        newReply.setUser(retrievedUser);
        newReply.setPost(retrievedPost);


        replyRepository.save(newReply);
    }
}
