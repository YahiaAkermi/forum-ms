package com.yahia.forum.dto;

import lombok.Data;

@Data
public class PostsDto {


    private String postTitle;

    private String postContent;

    private UserDto userDto;
}
