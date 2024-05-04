package com.yahia.forum.service;

import com.yahia.forum.dto.PostWithRepliesDto;
import com.yahia.forum.dto.PostsDto;
import com.yahia.forum.dto.PostsDtoWithId;
import com.yahia.forum.dto.ReplyDto;

import java.util.Collection;

public  interface IReplyService {

    /**
     *this method will create reply
     *@param replyDto -  ReplyDto  object
     */

     void createReply(ReplyDto replyDto);



    /**
     * Fetches replies of a given post
     *
     * @param postId - of type String
     * @return collection of Replies
     */
    PostWithRepliesDto fetchPostReplies(String postId);

}
