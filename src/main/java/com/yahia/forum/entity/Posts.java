package com.yahia.forum.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Data @AllArgsConstructor @NoArgsConstructor
public class Posts extends BaseEntity{

    @Id
    private String postId;

    private String postTitle;

    private String postContent;

    private String idGroup;

    @Lob
    @Column(name = "image", columnDefinition = "BLOB")
    private byte[] image;

    @ManyToOne
    @JoinColumn(name = "idPostCreator")
    private User user;

    @OneToMany(mappedBy = "post")
    private Collection<Reply> replies;


}
