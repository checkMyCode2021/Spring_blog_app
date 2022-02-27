package com.example.spring_blog_app.controller;

import com.example.spring_blog_app.model.Category;
import com.example.spring_blog_app.model.Post;
import com.example.spring_blog_app.service.PostService;
import com.example.spring_blog_app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

@Controller
public class BlogController {

    private UserService userService;
    private PostService postService;

    @Autowired
    public BlogController(UserService userService, PostService postService) {
        this.userService = userService;
        this.postService = postService;
    }


    @GetMapping("/")
    public String home(Model model){
        model.addAttribute("posts", postService.getAllPosts());
        return "index";
    }

    @GetMapping("/posts&{postId}")
    public String getPost(
            @PathVariable("postId") int postId, Model model){
        Optional<Post> postOptional = postService.getPostById(postId);
        if (postOptional.isPresent()) {
            model.addAttribute("post", postOptional.get());
        }
        return "post";
    }

    @GetMapping("/addPost")
    public String addPost(Model model){
        model.addAttribute("post", new Post());
        model.addAttribute("categories", new ArrayList<>(Arrays.asList(Category.values())));
        return "addPost";
    }

    @PostMapping("addPost")
    public String addPost(
            @ModelAttribute Post post
    ){
        postService.addPost(post.getTitle(), post.getContent(),post.getCategory(),userService.getUserById(1).get());
        return "redirect:/";
    }
}
