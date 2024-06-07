package com.yahia.forum.dto;

import jakarta.persistence.Column;
import jakarta.persistence.Lob;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class PostsDto {


    @NotEmpty(message = "Post title cannot be empty or null value")
    @Size(min = 3 ,max = 80,message = "title should be between 3 to 80 character")
    private String postTitle;

    @NotEmpty(message = "Post content cannot be empty or null value")
    @Size(min = 5 ,message = "the content should be at least 5 characters ")
    private String postContent;

    @NotEmpty(message = "Post image cannot be empty or null value")
    private String image;   // Base64 encoded string


    private UserDto userDto;
}
