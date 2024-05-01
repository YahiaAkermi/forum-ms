package com.yahia.forum.service.impl;

import com.yahia.forum.dto.PostsDto;
import com.yahia.forum.dto.UserDto;
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
import java.util.Collection;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

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

    /**
     * Fetches all posts
     *
     * @return collection of posts
     */
    @Override
    public Collection<PostsDto> fetchAllPosts() {

        // Fetch all posts from the repository
        Collection<Posts> postsCollection = postsRepository.findAll();


        // Convert the collection of Posts objects to a collection of PostsDto objects using the Stream API
        Collection<PostsDto> postsDtoCollection = postsCollection.stream()
                .map(post -> {
                    //here i transform the post retrieved to postdto
                    PostsDto postDto = PostsMapper.mapToPostsDTo(post, new PostsDto());
                    //then I set the postCreator when retrieving the post
                    postDto.setUserDto(UserMapper.mapToUserDTo2(userRepository.findById(post.getUser().getUserId()),new UserDto()) );
                    return postDto;
                })
                .collect(Collectors.toList());

        // Return the collection of PostsDto objects
        return postsDtoCollection;

    }

}
