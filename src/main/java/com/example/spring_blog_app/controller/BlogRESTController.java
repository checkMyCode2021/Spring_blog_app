package com.example.spring_blog_app.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BlogRESTController {

    @GetMapping("/")
    public String home (){
        return "Hello in homepage";
    }

}
