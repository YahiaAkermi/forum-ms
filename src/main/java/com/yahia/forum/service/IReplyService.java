package com.yahia.forum.service;

import com.yahia.forum.dto.*;

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


    /**
     *
     * @param replyWithIdtDto - ReplyWithIdtDto object
     * @return boolean indicaing if updating the reply was successfull or not
     */
    boolean updateReply(ReplyWithIdtDto replyWithIdtDto);

}
