package com.yahia.forum.service;

import com.yahia.forum.dto.PostsDto;
import com.yahia.forum.dto.ReplyDto;

public  interface IReplyService {

    /**
     *this method will create reply
     *@param replyDto -  ReplyDto  object
     */

     void createReply(ReplyDto replyDto);

}
