package com.yahia.forum.service.impl;

import com.yahia.forum.dto.PostsDto;
import com.yahia.forum.dto.PostsDtoWithId;
import com.yahia.forum.dto.UserDto;
import com.yahia.forum.entity.Posts;
import com.yahia.forum.entity.User;
import com.yahia.forum.entity.enums.UserType;
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
    public Collection<PostsDtoWithId> fetchAllPosts() {

        // Fetch all posts from the repository
        Collection<Posts> postsCollection = postsRepository.findAll();


        // Convert the collection of Posts objects to a collection of PostsDto objects using the Stream API
        Collection<PostsDtoWithId> postsDtoCollection = postsCollection.stream()
                .map(post -> {
                    //here i transform the post retrieved to postdto
                    PostsDtoWithId postsDtoWithId = PostsMapper.mapToPostsDToWithId(post, new PostsDtoWithId());
                    //then I set the postCreator when retrieving the post
                    postsDtoWithId.setUserDto(UserMapper.mapToUserDTo2(userRepository.findById(post.getUser().getUserId()),new UserDto()) );
                    return postsDtoWithId;
                })
                .collect(Collectors.toList());

        // Return the collection of PostsDto objects
        return postsDtoCollection;

    }

    /**
     * Fetches posts by user_type
     *
     * @param userType - user type could be student, teacher, admin
     * @return collection of posts
     */
    @Override
    public Collection<PostsDtoWithId> fetchPostsByUserType(UserType userType) {
        // Fetch all posts from the repository
        Collection<Posts> postsCollection = postsRepository.findAll();

        // Filter and convert the collection of Posts objects to a collection of PostsDto objects using the Stream API
        Collection<PostsDtoWithId> postsDtoCollection = postsCollection.stream()
                // Map each post to a PostsDto object
                .map(post -> {
                    // Transform the post retrieved to postDto
                    PostsDtoWithId postsDtoWithId = PostsMapper.mapToPostsDToWithId(post, new PostsDtoWithId());
                    // Find the user associated with the post and map them to a UserDto object
                    UserDto userDto = UserMapper.mapToUserDTo(post.getUser(), new UserDto());
                    // Set the userDto in the postDto
                    postsDtoWithId.setUserDto(userDto);
                    // Return the postDto object
                    return postsDtoWithId;
                })
                // Filter the collection of PostsDto objects based on userType
                .filter(postsDtoWithId -> postsDtoWithId.getUserDto().getUserType() == userType)
                // Collect the filtered postsDto objects into a list
                .collect(Collectors.toList());

        // Return the collection of filtered PostsDto objects
        return postsDtoCollection;
    }

    /**
     * @param postDto - PostsDto object
     * @return boolean indicating if the post is updated or not
     */
    @Override
    public boolean updatePost(PostsDto postDto) {
        return true;
    }


}
