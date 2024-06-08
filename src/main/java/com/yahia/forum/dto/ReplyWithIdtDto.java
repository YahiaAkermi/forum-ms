package com.yahia.forum.dto;

import lombok.Data;

@Data
public class ReplyWithIdtDto {

    private String replyId;

    private String replierEmail;

    private String replierUsername;

    private String replyContent;

}
