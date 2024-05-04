package com.yahia.forum.service.impl;

import com.yahia.forum.dto.*;
import com.yahia.forum.entity.Posts;
import com.yahia.forum.entity.Reply;
import com.yahia.forum.entity.User;
import com.yahia.forum.exception.ResourceNotFoundException;
import com.yahia.forum.mapper.PostsMapper;
import com.yahia.forum.mapper.ReplyMapper;
import com.yahia.forum.mapper.UserMapper;
import com.yahia.forum.repository.PostsRepository;
import com.yahia.forum.repository.ReplyRepository;
import com.yahia.forum.repository.UserRepository;
import com.yahia.forum.service.IReplyService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

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

    /**
     * Fetches replies of a given post
     *
     * @param postId - of type String
     * @return post with id and its replies
     */
    @Override
    public PostWithRepliesDto fetchPostReplies(String postId) {

        // Check if the post exists
        Posts retrievedPost = postsRepository.findByPostId(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "post ID", postId));

        // Retrieve related replies
        Collection<Reply> retrievedReplies = replyRepository.findRepliesByPost(retrievedPost);

        // Transforming reply to ReplyWithIdDto using Stream API and collecting to a list
        List<ReplyWithIdtDto> repliesWithIdDto = retrievedReplies.stream()
                .map(reply -> {
                    ReplyWithIdtDto replyWithIdDto = ReplyMapper.mapFromReplyToReplyWithIdDto(reply,new ReplyWithIdtDto());
                    replyWithIdDto.setReplyId(reply.getReplyId());
                    return replyWithIdDto;
                })
                .collect(Collectors.toList());

        // Map the retrieved post to PostsDtoWithId
        PostsDtoWithId postsDtoWithId = PostsMapper.mapToPostsDToWithId(retrievedPost,new PostsDtoWithId());

        //getting the user who posted then transforming him to UserDto
        User userWhoPosted=userRepository.findUserByPosts(retrievedPost).orElseThrow(
                () -> new ResourceNotFoundException("User","post ID",postId)
        );
        postsDtoWithId.setUserDto(UserMapper.mapToUserDTo(userWhoPosted,new UserDto()));

        // Create a PostWithRepliesDto object and return it
        PostWithRepliesDto postWithRepliesDto = new PostWithRepliesDto(postsDtoWithId, repliesWithIdDto);

        return postWithRepliesDto;
    }

    /**
     * Fetches replies of a given user in single post or subject
     *
     * @param replierUsername - of type String
     * @param postTitle - of type String
     * @return collection of PostWithRepliesDto
     */
    @Override
    public PostWithRepliesDto fetchRepliesOfParticularUserInASinglePost(String replierUsername,String postTitle) {

        //checking if the Post already exists post title is not case-sensitive
        Posts retrievedPost=postsRepository.findByPostTitleContainingIgnoreCase(postTitle).orElseThrow(
                ()-> new ResourceNotFoundException("Post","post title",postTitle)
        );

        //cheking if the User already exists username should be accurate
        User retrievedUser=userRepository.findUserByUsernameContains(replierUsername).orElseThrow(
                ()-> new ResourceNotFoundException("User","username",replierUsername)
        );

        //collecting replies for particular USER in a single post or subject after turning them to replies with ID
        Collection<ReplyWithIdtDto> relatedRepliesofUserOnSinglePost=new ArrayList<>();

        retrievedPost.getReplies().forEach(
                reply -> {
                    if(reply.getUser().getUsername().equals(replierUsername)){
                        relatedRepliesofUserOnSinglePost.add(ReplyMapper.mapFromReplyToReplyWithIdDto(reply,new ReplyWithIdtDto()));
                    }
                }
        );


        //mapping to post dto with id then  setting the post creator

        PostsDtoWithId postsDtoWithId=PostsMapper.mapToPostsDToWithId(retrievedPost,new PostsDtoWithId());
        postsDtoWithId.setUserDto(UserMapper.mapToUserDTo(retrievedPost.getUser(),new UserDto()));



        //forming my PostWithReplies dto , so I can expose it to the client
        PostWithRepliesDto postWithRepliesDto=new PostWithRepliesDto(postsDtoWithId,relatedRepliesofUserOnSinglePost);


        return postWithRepliesDto;
    }

    /**
     * @param replyWithIdtDto - ReplyWithIdtDto object
     * @return boolean indicaing if updating the reply was successfull or not
     */
    @Override
    public boolean updateReply(ReplyWithIdtDto replyWithIdtDto) {

        //checking if the replies already exists
        Reply retrievedReply= replyRepository.findReplyByReplyId(replyWithIdtDto.getReplyId()).orElseThrow(
                () -> new ResourceNotFoundException("Reply","reply ID", replyWithIdtDto.getReplyId())
        );

        //mapping replyDto to repy so i can save it in db
        Reply newReply=ReplyMapper.mapFromRelyDtoWithIdToReply(replyWithIdtDto,retrievedReply);

        //saving it to db
        replyRepository.save(newReply);

        return true;
    }

    /**
     * @param replyId - Input reply ID
     * @return boolean indicaing if the reply was deleted or not
     */
    @Override
    public boolean deleteReply(String replyId) {

        //checking if the reply exists
        Reply retrievedReply=replyRepository.findReplyByReplyId(replyId).orElseThrow(
                () -> new ResourceNotFoundException("Reply","reply ID",replyId)
        );

        //deleting reply
        replyRepository.delete(retrievedReply);

        return true;
    }


}
