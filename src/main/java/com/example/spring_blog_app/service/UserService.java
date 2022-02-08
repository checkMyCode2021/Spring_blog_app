package com.example.spring_blog_app.service;

import com.example.spring_blog_app.model.User;
import com.example.spring_blog_app.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    public void registerUser(User user){
        userRepository.save(user);

    }
}
