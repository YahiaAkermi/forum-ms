package com.yahia.forum.mapper;

import com.yahia.forum.dto.PostsDto;
import com.yahia.forum.dto.PostsDtoWithId;
import com.yahia.forum.dto.UserDto;
import com.yahia.forum.dto.UserDtoWithId;
import com.yahia.forum.entity.Posts;
import com.yahia.forum.entity.User;
import com.yahia.forum.repository.UserRepository;
import com.yahia.forum.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;

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
        postsDtoWithId.setImage(postsDtoWithId.getImage());
        return postsDtoWithId;
    }



    public static Posts mapToPosts2(PostsDtoWithId postsDtoWithId,Posts post ) {
        post.setPostTitle(postsDtoWithId.getPostTitle());
        post.setPostContent(postsDtoWithId.getPostContent());
        post.setPostId(postsDtoWithId.getPostId());
        post.setIdGroup(postsDtoWithId.getUserDto().getIdUserGroup());
        post.setImage(Utils.decodeBase64ToImage(postsDtoWithId.getImage()));
        post.setUser(UserMapper.mapToUser(postsDtoWithId.getUserDto(),new User()));
        return post;
    }
}
