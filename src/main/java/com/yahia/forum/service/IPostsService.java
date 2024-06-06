package com.yahia.forum.service;

import com.yahia.forum.dto.PostsDto;
import com.yahia.forum.dto.PostsDtoWithId;
import com.yahia.forum.entity.enums.UserType;
import com.yahia.forum.model.UserAuth;

import java.util.Collection;

public interface IPostsService {


    /**
     *this method will create post
     *@param postsDto -  PostsDto  object
     */
    void createPost(PostsDto postsDto, UserAuth userAuth);



    /**
     * Fetches all posts
     *
     * @param - empty
     * @return collection of posts
     */
    Collection<PostsDtoWithId> fetchAllPosts();


    /**
     * Fetches posts by user_type
     *
     * @param userType - user type could be student , teacher , admin
     * @return collection of posts
     */
    Collection<PostsDtoWithId> fetchPostsByUserType(UserType userType);


    /**
     * Fetches posts by post title
     *
     * @param postTitle - post title
     * @return collection of posts
     */
    Collection<PostsDtoWithId> fetchPostsByTitle(String postTitle);


    /**
     *
     * @param postsDtoWithId - PostsDto object
     * @return boolean indicating if the post is updated or not
     */
    boolean updatePost(PostsDtoWithId postsDtoWithId);



    /**
     *
     * @param postId - Input Post ID
     * @return boolean indicaing if the post was deleted or not
     */
    boolean deletePost(String postId);

}
