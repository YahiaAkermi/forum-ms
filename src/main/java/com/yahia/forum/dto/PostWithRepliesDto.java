package com.yahia.forum.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Collection;

@Data @AllArgsConstructor
public class PostWithRepliesDto {


    private PostsDtoWithId post;

    private Collection<ReplyWithIdtDto> replies;
}
