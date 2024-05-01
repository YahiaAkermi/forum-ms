package com.yahia.forum.service;

import com.yahia.forum.dto.PostsDto;
import com.yahia.forum.dto.UserDto;

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
     * @param  - empty
     * @return collection of posts
     */
    Collection<PostsDto> fetchAllPosts();

}
