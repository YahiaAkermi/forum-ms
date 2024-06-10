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
import com.yahia.forum.utils.Utils;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.*;
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
        Optional<User> retrievedUser=userRepository.findUserByEmail(replyDto.getEmail());

        //if the user is not in my db yet then , I add it
        if(retrievedUser.isEmpty()){
            User newUser=new User(null,replyDto.getUsername(),replyDto.getEmail(),null,null);
            //i save it in my db
            User userInMyDbNow=userRepository.save(newUser);

            //create id for reply
            String replyId= UUID.randomUUID().toString();

            //mapping to reply ,so I can store it in db
            Reply newReply= ReplyMapper.mapToReply(replyDto,new Reply());
            newReply.setReplyId(replyId);
            newReply.setUser(userInMyDbNow);
            newReply.setPost(retrievedPost);

            replyRepository.save(newReply);
        }else {

            //create id for reply
            String replyId= UUID.randomUUID().toString();

            //mapping to reply ,so I can store it in db
            Reply newReply= ReplyMapper.mapToReply(replyDto,new Reply());
            newReply.setReplyId(replyId);
            newReply.setUser(retrievedUser.get());
            newReply.setPost(retrievedPost);


            replyRepository.save(newReply);

        }


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
                    ReplyWithIdtDto replyWithIdDto = ReplyMapper.mapFromReplyToReplyWithIdDto(reply, new ReplyWithIdtDto());
                    replyWithIdDto.setReplyId(reply.getReplyId());
                    replyWithIdDto.setReplierEmail(reply.getUser().getEmail());
                    return replyWithIdDto;
                })
                .collect(Collectors.toList());

        // Map the retrieved post to PostsDtoWithId
        PostsDtoWithId postsDtoWithId = PostsMapper.mapToPostsDToWithId(retrievedPost, new PostsDtoWithId());
        postsDtoWithId.setImage(Utils.encodeImageToBase64(retrievedPost.getImage()));

        // getting the user who posted then transforming him to UserDto
        User userWhoPosted = userRepository.findUserByPosts(retrievedPost).orElseThrow(
                () -> new ResourceNotFoundException("User", "post ID", postId)
        );

        // to add idUserGroup
        UserDto myUserDto = new UserDto();
        myUserDto.setIdUserGroup(retrievedPost.getIdGroup());

        // then i set the userDto to postsDtoWithId
        postsDtoWithId.setUserDto(UserMapper.mapToUserDTo(userWhoPosted, myUserDto));

        // Create a PostWithRepliesDto object and return it
        PostWithRepliesDto postWithRepliesDto = new PostWithRepliesDto(
                retrievedPost.getCreatedAt(),
                retrievedPost.getUpdatedAt(),
                postsDtoWithId,
                repliesWithIdDto
        );

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
    public PostWithRepliesDto fetchRepliesOfParticularUserInASinglePost(String replierUsername, String postTitle) {
        // checking if the Post already exists post title is not case-sensitive
        Posts retrievedPost = postsRepository.findByPostTitleContainingIgnoreCase(postTitle).orElseThrow(
                () -> new ResourceNotFoundException("Post", "post title", postTitle)
        );

        // checking if the User already exists username should be accurate
        User retrievedUser = userRepository.findUserByUsernameContains(replierUsername).orElseThrow(
                () -> new ResourceNotFoundException("User", "username", replierUsername)
        );

        // collecting replies for particular USER in a single post or subject after turning them to replies with ID
        Collection<ReplyWithIdtDto> relatedRepliesOfUserOnSinglePost = retrievedPost.getReplies().stream()
                .filter(reply -> reply.getUser().getUsername().equals(replierUsername))
                .map(reply -> {
                    ReplyWithIdtDto replyWithIdtDto = ReplyMapper.mapFromReplyToReplyWithIdDto(reply, new ReplyWithIdtDto());
                    replyWithIdtDto.setReplyId(reply.getReplyId());
                    replyWithIdtDto.setReplierEmail(reply.getUser().getEmail());
                    return replyWithIdtDto;
                })
                .collect(Collectors.toList());

        // mapping to post dto with id then setting the post creator
        PostsDtoWithId postsDtoWithId = PostsMapper.mapToPostsDToWithId(retrievedPost, new PostsDtoWithId());
        postsDtoWithId.setImage(Utils.encodeImageToBase64(retrievedPost.getImage()));

        // setting the idGroup to userDto
        UserDto myUserDto = new UserDto();
        myUserDto.setIdUserGroup(retrievedPost.getIdGroup());
        postsDtoWithId.setUserDto(UserMapper.mapToUserDTo(retrievedPost.getUser(), myUserDto));

        // forming my PostWithReplies dto, so I can expose it to the client
        PostWithRepliesDto postWithRepliesDto = new PostWithRepliesDto(
                retrievedPost.getCreatedAt(),
                retrievedPost.getUpdatedAt(),
                postsDtoWithId,
                relatedRepliesOfUserOnSinglePost
        );

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

    @Override
    public Collection<PostWithRepliesDto> fetchAllPostsWithTheirReplies() {
        Sort sort = Sort.by(Sort.Order.desc("createdAt"), Sort.Order.desc("updatedAt"));
        List<Posts> allPosts = postsRepository.findAll(sort);

        return allPosts.stream().map(posts -> {
            // Turning it to PostsDtoWithId
            PostsDtoWithId postsDtoWithId = PostsMapper.mapToPostsDToWithId(posts, new PostsDtoWithId());
            postsDtoWithId.setImage(Utils.encodeImageToBase64(posts.getImage()));

            // Setting its UserDto
            UserDto myUserDto = new UserDto();
            myUserDto.setIdUserGroup(posts.getIdGroup());

            Optional<User> retrievedUser = userRepository.findUserByPosts(posts);
            if (retrievedUser.isPresent()) {
                User user = retrievedUser.get();
                myUserDto.setEmail(user.getEmail());
                myUserDto.setUsername(user.getUsername());
            }

            postsDtoWithId.setUserDto(myUserDto);

            // Forming the replies for the post
            Collection<Reply> retrievedReplies = replyRepository.findRepliesByPost(posts);

            List<ReplyWithIdtDto> repliesWithIdDto = retrievedReplies.stream()
                    .map(reply -> {
                        ReplyWithIdtDto replyWithIdDto = ReplyMapper.mapFromReplyToReplyWithIdDto(reply, new ReplyWithIdtDto());
                        replyWithIdDto.setReplyId(reply.getReplyId());
                        replyWithIdDto.setReplierEmail(reply.getUser().getEmail());
                        return replyWithIdDto;
                    })
                    .collect(Collectors.toList());

            return new PostWithRepliesDto(posts.getCreatedAt(),posts.getUpdatedAt(),postsDtoWithId, repliesWithIdDto);
        }).collect(Collectors.toList());
    }

    /**
     * Fetches posts filtered by content
     *
     * @param content - the content to filter by
     * @return collection of PostWithRepliesDto
     */
    public Collection<PostWithRepliesDto> filterPostsByContent(String content,String idGroup) {
        // Fetch posts that contain the specified content and in which group the post is present
        List<Posts> filteredPosts = postsRepository.findPostsByPostContentContainingIgnoreCaseAndIdGroupContainingIgnoreCase(content,idGroup);

        return filteredPosts.stream().map(posts -> {
            // Map to PostsDtoWithId
            PostsDtoWithId postsDtoWithId = PostsMapper.mapToPostsDToWithId(posts, new PostsDtoWithId());
            postsDtoWithId.setImage(Utils.encodeImageToBase64(posts.getImage()));

            // Set the UserDto
            UserDto myUserDto = new UserDto();
            myUserDto.setIdUserGroup(posts.getIdGroup());

            Optional<User> retrievedUser = userRepository.findUserByPosts(posts);
            if (retrievedUser.isPresent()) {
                User user = retrievedUser.get();
                myUserDto.setEmail(user.getEmail());
                myUserDto.setUsername(user.getUsername());
            }

            postsDtoWithId.setUserDto(myUserDto);

            // Form the replies for the post
            Collection<Reply> retrievedReplies = replyRepository.findRepliesByPost(posts);

            List<ReplyWithIdtDto> repliesWithIdDto = retrievedReplies.stream()
                    .map(reply -> {
                        ReplyWithIdtDto replyWithIdDto = ReplyMapper.mapFromReplyToReplyWithIdDto(reply, new ReplyWithIdtDto());
                        replyWithIdDto.setReplyId(reply.getReplyId());
                        replyWithIdDto.setReplierEmail(reply.getUser().getEmail());
                        return replyWithIdDto;
                    })
                    .collect(Collectors.toList());

            return new PostWithRepliesDto(posts.getCreatedAt(),posts.getUpdatedAt(),postsDtoWithId, repliesWithIdDto);
        }).collect(Collectors.toList());
    }


    /**
     * Fetches posts filtered by group ID
     *
     * @param idGroup - the group ID to filter by
     * @return collection of PostWithRepliesDto
     */
    public Collection<PostWithRepliesDto> filterPostsByGroupId(String idGroup) {
        // Fetch posts that belong to the specified group ID
        Collection<Posts> filteredPosts = postsRepository.findPostsByIdGroup(idGroup);

        return filteredPosts.stream().map(posts -> {
            // Map to PostsDtoWithId
            PostsDtoWithId postsDtoWithId = PostsMapper.mapToPostsDToWithId(posts, new PostsDtoWithId());
            postsDtoWithId.setImage(Utils.encodeImageToBase64(posts.getImage()));

            // Set the UserDto
            UserDto myUserDto = new UserDto();
            myUserDto.setIdUserGroup(posts.getIdGroup());

            Optional<User> retrievedUser = userRepository.findUserByPosts(posts);
            if (retrievedUser.isPresent()) {
                User user = retrievedUser.get();
                myUserDto.setEmail(user.getEmail());
                myUserDto.setUsername(user.getUsername());
            }

            postsDtoWithId.setUserDto(myUserDto);

            // Form the replies for the post
            Collection<Reply> retrievedReplies = replyRepository.findRepliesByPost(posts);

            Collection<ReplyWithIdtDto> repliesWithIdDto = retrievedReplies.stream()
                    .map(reply -> {
                        ReplyWithIdtDto replyWithIdDto = ReplyMapper.mapFromReplyToReplyWithIdDto(reply, new ReplyWithIdtDto());
                        replyWithIdDto.setReplyId(reply.getReplyId());
                        replyWithIdDto.setReplierEmail(reply.getUser().getEmail());
                        return replyWithIdDto;
                    })
                    .collect(Collectors.toList());



            return new PostWithRepliesDto(posts.getCreatedAt(),posts.getUpdatedAt(),postsDtoWithId, repliesWithIdDto);
        }).collect(Collectors.toList());
    }

    /**
     * Fetch all groups that a certain user is in then all posts of that group with their replies
     *
     * @param email - email of the user
     * @return collection of collection PostWithRepliesDto
     */
    @Override
    public Collection<Collection<PostWithRepliesDto>> fetchGroupsOfParticularUser(String email) {

        // Retrieve the user by email
        User retrievedUser = userRepository.findUserByEmail(email).orElseThrow(
                () -> new ResourceNotFoundException("User", "email", email)
        );

        // Fetch all posts by the user
        Collection<Posts> userPosts = postsRepository.findPostsByUser(retrievedUser);

        // Extract unique group IDs from the user's posts
        Set<String> uniqueGroupIds = userPosts.stream()
                .map(Posts::getIdGroup)
                .collect(Collectors.toSet());

        // Collection to hold posts with replies for each group
        Collection<Collection<PostWithRepliesDto>> groupsPostsWithReplies = new ArrayList<>();

        for (String groupId : uniqueGroupIds) {
            // Fetch posts of the current group using the existing method
            Collection<PostWithRepliesDto> postsWithRepliesDtos = filterPostsByGroupId(groupId);

            // Add the posts with replies of the current group to the main collection
            groupsPostsWithReplies.add(postsWithRepliesDtos);
        }

        // Adding general group also
        Collection<PostWithRepliesDto> generalGroupPosts = filterPostsByGroupId("general");
        groupsPostsWithReplies.add(generalGroupPosts);

        return groupsPostsWithReplies;
    }






}
