package com.yahia.forum.service;

import com.yahia.forum.dto.PostsDto;
import com.yahia.forum.dto.UserDto;

public interface IPostsService {


    /**
     *this method will create post
     *@param postsDto -  PostsDto  object
     */

    void createPost(PostsDto postsDto);

}
