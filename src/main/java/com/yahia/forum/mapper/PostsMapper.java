package com.yahia.forum.mapper;

import com.yahia.forum.dto.PostsDto;
import com.yahia.forum.dto.PostsDtoWithId;
import com.yahia.forum.dto.UserDto;
import com.yahia.forum.entity.Posts;
import com.yahia.forum.entity.User;

public class PostsMapper {

    public static PostsDto mapToPostsDTo(Posts post, PostsDto postsDto) {
        postsDto.setPostTitle(post.getPostTitle());
        postsDto.setPostContent(post.getPostContent());
        return postsDto;
    }

    public static Posts mapToPost(PostsDto postsDto, Posts post) {
        post.setPostTitle(postsDto.getPostTitle());
        post.setPostContent(postsDto.getPostContent());
        return post;
    }


    public static PostsDtoWithId mapToPostsDToWithId(Posts post, PostsDtoWithId postsDtoWithId) {
        postsDtoWithId.setPostTitle(post.getPostTitle());
        postsDtoWithId.setPostContent(post.getPostContent());
        postsDtoWithId.setPostId(post.getPostId());
        return postsDtoWithId;
    }
}
