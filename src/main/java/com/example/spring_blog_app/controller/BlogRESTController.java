package com.example.spring_blog_app.controller;

import com.example.spring_blog_app.model.User;
import com.example.spring_blog_app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
public class BlogRESTController {

    @Autowired
    UserService userService;

    @GetMapping("/")
    public String home (){
        return "Hello in homepage";
    }

    @PostMapping("/user/register")
    public void registerUser(
            @RequestParam("email") String email,
            @RequestParam("password") String password
    ){
        User user = new User(email,password, LocalDateTime.now(),false);
        userService.registerUser(user);
    }
}
