package com.yahia.forum.mapper;

import com.yahia.forum.dto.PostsDto;
import com.yahia.forum.dto.UserDto;
import com.yahia.forum.entity.Posts;
import com.yahia.forum.entity.User;

public class PostsMapper {

    public static PostsDto mapToPostsDTo(Posts post, PostsDto postsDto) {
        postsDto.setPostTitle(post.getPostTitle());
        postsDto.setPostContent(post.getPostContent());
        return postsDto;
    }

    public static Posts mapToUser(PostsDto postsDto, Posts post) {
        post.setPostTitle(postsDto.getPostTitle());
        post.setPostContent(postsDto.getPostContent());
        return post;
    }
}
