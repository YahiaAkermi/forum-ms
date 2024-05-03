package com.yahia.forum.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;

@Entity @Data @AllArgsConstructor @NoArgsConstructor
public class Posts extends BaseEntity{

    @Id
    private String postId;

    private String postTitle;

    private String postContent;

    @ManyToOne
    @JoinColumn(name = "idPostCreator")
    private User user;

    @OneToMany(mappedBy = "post")
    private Collection<Reply> replies;


}
