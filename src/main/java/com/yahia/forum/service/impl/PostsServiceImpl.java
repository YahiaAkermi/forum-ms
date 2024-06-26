package com.yahia.forum.service.impl;

import com.yahia.forum.dto.PostsDto;
import com.yahia.forum.dto.PostsDtoWithId;
import com.yahia.forum.dto.UserDto;
import com.yahia.forum.entity.Posts;
import com.yahia.forum.entity.User;
import com.yahia.forum.entity.enums.UserType;
import com.yahia.forum.exception.ResourceNotFoundException;
import com.yahia.forum.mapper.PostsMapper;
import com.yahia.forum.mapper.UserMapper;
import com.yahia.forum.repository.PostsRepository;
import com.yahia.forum.repository.ReplyRepository;
import com.yahia.forum.repository.UserRepository;
import com.yahia.forum.service.IPostsService;
import com.yahia.forum.utils.Utils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Base64;
import java.util.Collection;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service @AllArgsConstructor
public class PostsServiceImpl implements IPostsService {

    private UserRepository userRepository;
    private PostsRepository postsRepository;

    private ReplyRepository replyRepository;


    /**
     * this method will create post
     *
     * @param postsDto -  PostsDto  object
     */
    @Override
    public void createPost(PostsDto postsDto) {

        //transforming it to post, so we can store it in the db
        Posts post= PostsMapper.mapToPost(postsDto,new Posts());
        //indicating in which group the post belongs
        post.setIdGroup(postsDto.getUserDto().getIdUserGroup());


        // Decode base64 image
        byte[] imageBytes = Base64.getDecoder().decode(postsDto.getImage());
        post.setImage(imageBytes);

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

                    UserDto myUserDto=UserMapper.mapToUserDTo2(userRepository.findById(post.getUser().getUserId()),new UserDto());
                    myUserDto.setIdUserGroup(post.getIdGroup());
                    postsDtoWithId.setUserDto( myUserDto);


                    // Convert byte array to Base64 encoded string then adding it to postsDtoWithId
                    String base64Image = Base64.getEncoder().encodeToString(post.getImage());
                    postsDtoWithId.setImage(base64Image);



                    return postsDtoWithId;
                })
                .collect(Collectors.toList());

        // Return the collection of PostsDto objects
        return postsDtoCollection;

    }

    /**
     * Fetches posts by group
     *
     * @param group - the group where group of student are taught by a teacher
     * @return collection of posts
     */
    @Override
    public Collection<PostsDtoWithId> fetchPostsByGroup(String group) {
        // Fetch all posts from the repository
        Collection<Posts> postsCollection = postsRepository.findPostsByIdGroup(group);

        // Filter and convert the collection of Posts objects to a collection of PostsDto objects using the Stream API
        Collection<PostsDtoWithId> postsDtoCollection = postsCollection.stream()
                // Map each post to a PostsDto object
                .map(post -> {
                    // Transform the post retrieved to postDto
                    PostsDtoWithId postsDtoWithId = PostsMapper.mapToPostsDToWithId(post, new PostsDtoWithId());
                    postsDtoWithId.setImage(Utils.encodeImageToBase64(post.getImage()));

                    // Find the user associated with the post and map them to a UserDto object
                    UserDto userDto = UserMapper.mapToUserDTo(post.getUser(), new UserDto());
                    userDto.setIdUserGroup(post.getIdGroup());
                    // Set the userDto in the postDto
                    postsDtoWithId.setUserDto(userDto);
                    // Return the postDto object
                    return postsDtoWithId;
                })
                // Filter the collection of PostsDto objects based on userType
//                .filter(postsDtoWithId -> postsDtoWithId.getUserDto().getUserType() == userType)
                // Collect the filtered postsDto objects into a list
                .collect(Collectors.toList());

        // Return the collection of filtered PostsDto objects
        return postsDtoCollection;
    }

    /**
     * Fetches posts by post title
     *
     * @param postTitle - post title
     * @return collection of posts
     */
    @Override
    public Collection<PostsDtoWithId> fetchPostsByTitle(String postTitle) {

        //fetching the posts with the title mentioned
        Collection<Posts> postsRetrieved=postsRepository.findPostsByPostTitleContainsIgnoreCase(postTitle);

        //turning them to posts dtos with IDs
        Collection<PostsDtoWithId> postsDtoWithIds=postsRetrieved.stream().map(
                post ->{
                    //retrieve the post creator
                    User user=userRepository.findUserByPosts(post).orElseThrow(
                            ()-> new ResourceNotFoundException("User","post Id", post.getPostId())
                    );
                    //turning him to userDto ,so I can give it to the client
                    UserDto userDto=UserMapper.mapToUserDTo(user,new UserDto());
                    userDto.setIdUserGroup(post.getIdGroup());

                    //maaping to postWithId dto then adding the post creator details
                    PostsDtoWithId postsDtoWithId=PostsMapper.mapToPostsDToWithId(post,new PostsDtoWithId());
                    postsDtoWithId.setImage(Utils.encodeImageToBase64(post.getImage()));
                    postsDtoWithId.setUserDto(userDto);

                    return postsDtoWithId;
                }
        ).collect(Collectors.toList());

        return postsDtoWithIds;
    }

    /**
     * Fetches posts created by a particular user
     *
     * @param email - String
     * @return collection of posts
     */
    @Override
    public Collection<PostsDtoWithId> fetchPostsByEmail(String email) {

        User user=userRepository.findUserByEmail(email).orElseThrow(
                ()-> new ResourceNotFoundException("User","email", email)
        );

        Collection<Posts> retrievedPosts=postsRepository.findPostsByUser(user);

        //turning them to posts dtos with IDs
        Collection<PostsDtoWithId> postsDtoWithIds=retrievedPosts.stream().map(
                post ->{

                    //turning him to userDto ,so I can give it to the client
                    UserDto userDto=UserMapper.mapToUserDTo(user,new UserDto());
                    userDto.setIdUserGroup(post.getIdGroup());

                    //maaping to postWithId dto then adding the post creator details
                    PostsDtoWithId postsDtoWithId=PostsMapper.mapToPostsDToWithId(post,new PostsDtoWithId());
                    postsDtoWithId.setImage(Utils.encodeImageToBase64(post.getImage()));
                    postsDtoWithId.setUserDto(userDto);

                    return postsDtoWithId;
                }
        ).collect(Collectors.toList());

        return postsDtoWithIds;
    }

    /**
     * @param postsDtoWithId - PostsDtoWithId object
     * @return boolean indicating if the post is updated or not
     */
    @Override
    public boolean updatePost(PostsDtoWithId postsDtoWithId) {

        //checking if the user exists in postCreator table
      User retrievedUser=userRepository.findUserByEmail(postsDtoWithId.getUserDto().getEmail()).orElseThrow(
              () -> new ResourceNotFoundException("User","Email",postsDtoWithId.getUserDto().getEmail())
      );

      //checking if the post exists in posts table
      Posts  retrievedPost= postsRepository.findById(postsDtoWithId.getPostId()).orElseThrow(
              () -> new ResourceNotFoundException("Post","post ID",postsDtoWithId.getPostId())
      );



        //mapping to posts ,so I can the save the new one
       Posts newPost= PostsMapper.mapToPosts2(postsDtoWithId,new Posts());

       //if the user ever changes his username or userType but not his email
   User newUser=new User(retrievedUser.getUserId(),postsDtoWithId.getUserDto().getUsername(),postsDtoWithId.getUserDto().getEmail(),retrievedUser.getPosts(),retrievedUser.getReplies());

        //I should try to update the User table first


       //saving the user in posts with his new username or userType but not a new email after updating his infos in PostCreator table
      newPost.setUser(userRepository.save(newUser));

       //saving newPost to the db
       postsRepository.save(newPost);

        return true;
    }

    /**
     * @param postId - Input Post ID
     * @return boolean indicaing if the post was deleted or not
     */
    @Override
    public boolean deletePost(String postId) {
        // Find the post by ID
        Posts post = postsRepository.findById(postId).orElseThrow(
                () -> new ResourceNotFoundException("Posts", "postId", postId)
        );

        // Delete all replies associated with the post
        replyRepository.deleteByPostPostId(post.getPostId());

        // Delete the post
        postsRepository.deleteById(post.getPostId());

        return true;
    }


}
