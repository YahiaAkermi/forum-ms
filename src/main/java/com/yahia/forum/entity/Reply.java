package com.yahia.forum.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Reply extends BaseEntity {

    @Id
    private String replyId;


    private String replyContent;




    @ManyToOne
    @JoinColumn(name = "postId")
    private Posts post;


    private Long idUser;
}
