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
     * @return post with its replies
     */
    PostWithRepliesDto fetchPostReplies(String postId);

    /**
     * Fetches replies of a given user in single post or subject
     *
     * @param replierUsername - of type String
     * @param postTitle - of type String
     * @return collection of PostWithRepliesDto
     */
    PostWithRepliesDto fetchRepliesOfParticularUserInASinglePost(String replierUsername,String postTitle);


    /**
     *
     * @param replyWithIdtDto - ReplyWithIdtDto object
     * @return boolean indicaing if updating the reply was successfull or not
     */
    boolean updateReply(ReplyWithIdtDto replyWithIdtDto);


    /**
     *
     * @param replyId - Input reply ID
     * @return boolean indicaing if the reply was deleted or not
     */
    boolean deleteReply(String replyId);

    /**
     * with this function I'm going to return all the posts with their replies
     *
     * @return Collection of PostWithRepliesDto
     */
    Collection<PostWithRepliesDto> fetchAllPostsWithTheirReplies();


    /**
     * Fetches posts filtered by content
     *
     * @param content - the content to filter by
     * @param idGroup - the group where post is present
     * @return collection of PostWithRepliesDto
     */
    Collection<PostWithRepliesDto> filterPostsByContent(String content,String idGroup);


}
