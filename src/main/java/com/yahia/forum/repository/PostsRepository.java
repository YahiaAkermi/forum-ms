package com.yahia.forum.repository;

import com.yahia.forum.entity.Posts;
import com.yahia.forum.entity.User;
import org.hibernate.sql.ast.tree.expression.Collation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostsRepository extends JpaRepository<Posts,String> {

    Integer deletePostsByUser(User user);



}
