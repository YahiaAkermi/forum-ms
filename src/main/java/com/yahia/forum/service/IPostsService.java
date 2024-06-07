package com.yahia.forum.service;

import com.yahia.forum.dto.PostsDto;
import com.yahia.forum.dto.PostsDtoWithId;
import com.yahia.forum.entity.enums.UserType;

import java.util.Collection;

public interface IPostsService {


    /**
     *this method will create post
     *@param postsDto -  PostsDto  object
     */
    void createPost(PostsDto postsDto);



    /**
     * Fetches all posts
     *
     * @param - empty
     * @return collection of posts
     */
    Collection<PostsDtoWithId> fetchAllPosts();


    /**
     * Fetches posts by group
     *
     * @param group - the group where group of student are taught by a teacher
     * @return collection of posts
     */
    Collection<PostsDtoWithId> fetchPostsByGroup(String group);


    /**
     * Fetches posts by post title
     *
     * @param postTitle - post title
     * @return collection of posts
     */
    Collection<PostsDtoWithId> fetchPostsByTitle(String postTitle);

    /**
     * Fetches posts created by a particular user
     *
     * @param email - String
     * @return collection of posts
     */
    Collection<PostsDtoWithId> fetchPostsByEmail(String email);


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
