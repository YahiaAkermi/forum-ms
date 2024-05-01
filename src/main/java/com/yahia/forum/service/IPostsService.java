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
     * Fetches posts by user_type
     *
     * @param userType - user type could be student , teacher , admin
     * @return collection of posts
     */
    Collection<PostsDtoWithId> fetchPostsByUserType(UserType userType);


    /**
     *
     * @param postDto - PostsDto object
     * @return boolean indicating if the post is updated or not
     */
    boolean updatePost(PostsDto postDto);

}
