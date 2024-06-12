package com.yahia.forum.dto;


import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
public class PostsDtoWithId {

    @Pattern(regexp = "(^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$)",message = "postId isn't in UUID 32bit format please enter the right one")
    private String postId;

    @NotEmpty(message = "Post title cannot be empty or null value")
    @Size(min = 3 ,max = 80,message = "title should be between 3 to 80 character")
    private String postTitle;

    @NotEmpty(message = "Post content cannot be empty or null value")
    @Size(min = 5 ,max = 200,message = "the content should be between 5 to 200 character")
    private String postContent;

    @NotEmpty(message = "Post image cannot be empty or null value")
    private String image;   // Base64 encoded string


    private UserDto userDto;
}
