package com.yahia.forum.repository;

import com.yahia.forum.entity.Posts;
import com.yahia.forum.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {

    Optional<User> findUserByEmail(String email);
    Optional<User> findUserByUsernameContains(String email);

    Optional<User> findUserByPosts(Posts posts);

    boolean existsUserByUsername(String username);




}
