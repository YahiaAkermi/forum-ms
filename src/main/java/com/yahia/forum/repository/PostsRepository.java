package com.yahia.forum.repository;

import com.yahia.forum.entity.Posts;
import com.yahia.forum.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface PostsRepository extends JpaRepository<Posts,String> {

    Integer deletePostsByUser(User user);

    Optional<Posts> findByPostId(String postId);


    Collection<Posts> findPostsByUser(User user);

    Optional<Posts> findByPostTitleContainingIgnoreCase(String postTitle);

    Collection<Posts> findPostsByPostTitleContainsIgnoreCase(String postTitle);

    Collection<Posts> findPostsByIdGroup(String group);

    List<Posts> findPostsByPostContentContainingIgnoreCaseAndIdGroupContainingIgnoreCase(String content,String idGroup);






}
