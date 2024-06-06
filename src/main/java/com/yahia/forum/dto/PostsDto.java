package com.yahia.forum.dto;


import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
public class PostsDto {


    @NotEmpty(message = "Post title cannot be empty or null value")
    @Size(min = 3 ,max = 80,message = "title should be between 3 to 80 character")
    private String postTitle;

    @NotEmpty(message = "Post content cannot be empty or null value")
    @Size(min = 5 ,message = "the content should be at least 5 characters ")
    private String postContent;


}
