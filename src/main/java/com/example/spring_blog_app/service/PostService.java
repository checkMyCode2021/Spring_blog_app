package com.example.spring_blog_app.service;

import com.example.spring_blog_app.model.Category;
import com.example.spring_blog_app.model.Post;
import com.example.spring_blog_app.model.User;
import com.example.spring_blog_app.repositories.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PostService {

    @Autowired
    PostRepository postRepository;

    public void addPost(String title, String content, Category category, User author){
        postRepository.save(new Post(title, content, LocalDateTime.now(),category, author));
    }

    public List<Post> getAllPosts(){
       return postRepository.findAll(Sort.by(Sort.Direction.DESC,"dateAdded"));
    }
}
