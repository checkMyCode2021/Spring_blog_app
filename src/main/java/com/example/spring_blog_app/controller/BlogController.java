package com.example.spring_blog_app.controller;

import com.example.spring_blog_app.model.*;
import com.example.spring_blog_app.service.PostService;
import com.example.spring_blog_app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
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
    public String home(Model model, Authentication auth) {
        model.addAttribute("posts", postService.getAllPosts());
        model.addAttribute("auth", userService.getCredential(auth));
        return "index";
    }

    @GetMapping("/posts&{postId}")
    public String getPost(
            @PathVariable("postId") int postId, Model model, Authentication auth) {
        Optional<Post> postOptional = postService.getPostById(postId);
        if (postOptional.isPresent()) {
            model.addAttribute("post", postOptional.get());
            model.addAttribute("auth", userService.getCredential(auth));
        }
        return "post";
    }

    @GetMapping("/addPost")
    public String addPost(Model model, Authentication auth) {
        model.addAttribute("postDto", new PostDto());
        model.addAttribute("categories", new ArrayList<>(Arrays.asList(Category.values())));
        model.addAttribute("auth", userService.getCredential(auth));
        return "addPost";
    }

    @PostMapping("addPost")
    public String addPost(
            @Valid
            @ModelAttribute PostDto postDto,
            BindingResult bindingResult,
            Model model,
            Authentication auth
    ) {
        if (bindingResult.hasErrors()) {
            bindingResult.getFieldErrors().stream().forEach(fieldError -> System.out.println(fieldError.toString()));
            model.addAttribute("categories", new ArrayList<>(Arrays.asList(Category.values())));
            return "addPost";
        }
        UserDetails userDetails = (UserDetails) auth.getPrincipal();
        String loggedEmail = userDetails.getUsername();
        postService.addPost(postDto.getTitle(), postDto.getContent(), postDto.getCategory(), 
                userService.getUserByEmail(loggedEmail).get());
        return "redirect:/";
    }

    @GetMapping("/register")
    public String addUser(Model model, Authentication auth) {
        model.addAttribute("userDto", new UserDto());
        model.addAttribute("auth",userService.getCredential(auth));
        return "addUser";
    }

    @PostMapping("register")
    public String addUser(
            @Valid
            @ModelAttribute UserDto userDto,
            BindingResult bindingResult,
            Model model
    ) {
        if (bindingResult.hasErrors()) {
            return "addUser";
        }
        if (userService.getUserByEmail(userDto.getEmail()).isPresent()){
            model.addAttribute("emailError", "e-mail address is not unique");
            return "addUser";
        }
        userService.registerUser(new User(userDto.getEmail(), userDto.getPassword(), LocalDateTime.now(), true));
        return "redirect:/";
    }

    @GetMapping("/login")
    public String login(Model model, Authentication auth){
        model.addAttribute("auth", userService.getCredential(auth));
        return "login";
    }

    @GetMapping("/login&error={loginError}")
    public String login(@PathVariable("loginError") Boolean loginError, Model model, Authentication auth
    ) {
        System.out.println(loginError.getClass());
        model.addAttribute("loginError", loginError);
        model.addAttribute("auth", userService.getCredential(auth));
        return "login";
    }

    @GetMapping("/deletePost&{postId}")
    public String deletePost(@PathVariable("postId") int postId, Model model, Authentication auth){
        if (postService.getPostById(postId).isPresent()) {
            postService.deletePostById(postId);
            return "redirect:/";
        } else {
            model.addAttribute("errorMessage", "Delete action aborted! There is no post with id = " + postId);
            model.addAttribute("posts", postService.getAllPosts());
            model.addAttribute("auth", userService.getCredential(auth));
            return "index";
        }
    }

    @GetMapping("/updatePost&{postId}")
    public String updatePost(@PathVariable("postId") Integer postId, Model model, Authentication auth) {
        if (postService.getPostById(postId).isPresent()) {
            Post postToUpdate = postService.getPostById(postId).get();
            PostDto postDto = new PostDto(postToUpdate.getTitle(), postToUpdate.getContent(), postToUpdate.getCategory());
            model.addAttribute("postDto", postDto);
            model.addAttribute("postId", postId);
            model.addAttribute("categories", new ArrayList<>(Arrays.asList(Category.values())));
            model.addAttribute("auth", userService.getCredential(auth));
            return "addPost";
        }
        model.addAttribute("errorMessage", "Update action aborted! There is no post with id = " + postId);
        model.addAttribute("posts", postService.getAllPosts());
        model.addAttribute("auth", userService.getCredential(auth));
        return "index";    }

    @PostMapping("/updatePost&{postId}")
    public String udatePost(
            @PathVariable("postId") int postId,
            @Valid @ModelAttribute("postDto") PostDto postDto,
            BindingResult bindingResult,
            Model model
    ) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("categories", new ArrayList<>(Arrays.asList(Category.values())));
            return "addPost";
        }
        if (postService.updatePost(postId, postDto)) {
            return "redirect:/posts&" + postId;
        }
        return "redirect:/";
    }
}
