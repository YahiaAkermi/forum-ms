package com.yahia.forum.repository;

import com.yahia.forum.entity.Posts;
import com.yahia.forum.entity.Reply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Optional;

@Repository
public interface ReplyRepository extends JpaRepository<Reply,String> {

    Collection<Reply> findRepliesByPost(Posts post);

    Optional<Reply> findReplyByReplyId(String replyId);
}
