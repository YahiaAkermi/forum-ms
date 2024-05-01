package com.yahia.forum.service.impl;

import com.yahia.forum.dto.PostsDto;
import com.yahia.forum.entity.Posts;
import com.yahia.forum.entity.User;
import com.yahia.forum.mapper.PostsMapper;
import com.yahia.forum.mapper.UserMapper;
import com.yahia.forum.repository.PostsRepository;
import com.yahia.forum.repository.UserRepository;
import com.yahia.forum.service.IPostsService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service @AllArgsConstructor
public class PostsServiceImpl implements IPostsService {

    private UserRepository userRepository;
    private PostsRepository postsRepository;


    /**
     * this method will create post
     *
     * @param postsDto -  PostsDto  object
     */
    @Override
    public void createPost(PostsDto postsDto) {

        //transforming it to post, so we can store it in the db
        Posts post= PostsMapper.mapToPost(postsDto,new Posts());

        //checking if the user is post creator if not I insert the user then his post
       User user=UserMapper.mapToUser(postsDto.getUserDto(),new User());
       Optional<User> postCreator=userRepository.findUserByEmail(user.getEmail());

       if(postCreator.isEmpty()){
           //adding the post creator id for a user who never posted after saving him in the db
           user.setCreatedAt(LocalDateTime.now());
           user.setCreatedBy("admin");
           post.setUser(userRepository.save(user));
       }else{
           //adding the post creator id for user who is already a creator
           post.setUser(postCreator.get());

       }

        //genrating id for our post
        String postId = UUID.randomUUID().toString();
        post.setPostId(postId);

        //adding meta data
        post.setCreatedAt(LocalDateTime.now());
        post.setCreatedBy("admin");

        //saving it to the db
        postsRepository.save(post);


    }

}
