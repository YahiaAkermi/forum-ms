package com.yahia.forum.dto;

import lombok.Data;

@Data
public class ReplyDto {

    private String postId;

    private String email;

    private String username;

    private String replyContent;

}
