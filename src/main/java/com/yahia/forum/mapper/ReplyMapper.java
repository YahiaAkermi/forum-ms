package com.yahia.forum.mapper;

import com.yahia.forum.dto.PostsDto;
import com.yahia.forum.dto.ReplyDto;
import com.yahia.forum.dto.ReplyWithIdtDto;
import com.yahia.forum.entity.Posts;
import com.yahia.forum.entity.Reply;

public class ReplyMapper {

    public static ReplyDto mapToReplyDTo(Reply reply, ReplyDto replyDto) {
        replyDto.setReplyContent(reply.getReplyContent());
        replyDto.setUsername(reply.getUser().getUsername());
        replyDto.setPostId(reply.getPost().getPostId());
        return replyDto;
    }

    public static Reply mapToReply(ReplyDto replyDto, Reply reply) {
        reply.setReplyContent(replyDto.getReplyContent());
        return reply;
    }

    public static ReplyWithIdtDto mapFromReplyToReplyWithIdDto(Reply reply,ReplyWithIdtDto replyWithIdtDto){
        replyWithIdtDto.setReplyId(reply.getReplyId());
        replyWithIdtDto.setReplierUsername(reply.getUser().getUsername());
        replyWithIdtDto.setReplyContent(reply.getReplyContent());
        return replyWithIdtDto;
    }

    public static Reply mapFromRelyDtoWithIdToReply(ReplyWithIdtDto replyWithIdtDto, Reply reply) {
        reply.setReplyId(replyWithIdtDto.getReplyId());
        reply.setReplyContent(replyWithIdtDto.getReplyContent());
        return reply;
    }

}
