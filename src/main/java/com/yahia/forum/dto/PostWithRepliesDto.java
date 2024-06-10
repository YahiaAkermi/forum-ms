package com.yahia.forum.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Collection;

@Data @AllArgsConstructor
public class PostWithRepliesDto {


    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private PostsDtoWithId post;

    private Collection<ReplyWithIdtDto> replies;
}
