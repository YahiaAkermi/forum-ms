package com.yahia.forum.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Data @AllArgsConstructor @NoArgsConstructor
@Table(name = "PostCreator")
public class User extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(unique = true)
    private String username;

    @Column(unique = true)
    private String email;


    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private Collection<Posts> posts;

    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private Collection<Reply> replies;



}
