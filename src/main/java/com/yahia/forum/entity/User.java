package com.yahia.forum.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.yahia.forum.entity.enums.UserType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;

@Entity @Data @AllArgsConstructor @NoArgsConstructor
@Table(name = "PostCreator")
public class User extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    //lazm username ykoun unique
    private String username;

    //lazm email ykoun unique
    private String email;

    @Enumerated(EnumType.STRING)
    private UserType userType;

    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private Collection<Posts> posts;

}
