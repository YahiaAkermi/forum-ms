package com.yahia.forum.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity @Data @AllArgsConstructor @NoArgsConstructor
public class Posts extends BaseEntity{

    @Id
    private String postId;

    private String postTitle;

    private String postContent;

    @ManyToOne
    @JoinColumn(name = "idPostCreator")
    private User user;


}
