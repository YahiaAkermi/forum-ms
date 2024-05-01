package com.yahia.forum.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class PostsController {

    @PostMapping("/create")
    public String sayHello(){
        return "wee sheikh salem";
    }

}
