package com.yahia.forum.entity;


import com.yahia.forum.model.UserAuth;
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



    @OneToMany(mappedBy = "post")
    private Collection<Reply> replies;



    @Transient
    private UserAuth user;





}
