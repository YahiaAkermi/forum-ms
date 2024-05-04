package com.yahia.forum.repository;

import com.yahia.forum.entity.Posts;
import com.yahia.forum.entity.Reply;
import com.yahia.forum.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Optional;

@Repository
public interface ReplyRepository extends JpaRepository<Reply,String> {

    Collection<Reply> findRepliesByPost(Posts post);

    Collection<Reply> findRepliesByPostAndUser(Posts post, User user);

    Optional<Reply> findReplyByReplyId(String replyId);
}
