package com.yahia.forum.dto;

import lombok.Data;

@Data
public class PostsDtoWithId {

    private String postId;

    private String postTitle;

    private String postContent;

    private UserDto userDto;
}
