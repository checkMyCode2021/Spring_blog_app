package com.example.spring_blog_app.controller;

import com.example.spring_blog_app.model.Category;
import com.example.spring_blog_app.model.Post;
import com.example.spring_blog_app.model.User;
import com.example.spring_blog_app.service.PostService;
import com.example.spring_blog_app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.*;

@RequestMapping("/api")
@RestController
public class BlogRESTController {

    UserService userService;
    PostService postService;

    @Autowired
    public BlogRESTController(UserService userService, PostService postService) {
        this.userService = userService;
        this.postService = postService;
    }

    @GetMapping("/posts/byCategory")
    public List<Post> getPostByCategory(
            @RequestParam("category") Category category
    ){
        return postService.getPostsByCategory(category);
    }

    @GetMapping("/posts/byCategoryAndAuthor")
    public List<Post> getPostsByCategoryAndAuthor(
            @RequestParam("category") Category category,
            @RequestParam("author") int userId
    ) {
        if (userService.getUserById(userId).isPresent()) {
            return postService.getPostsByCategoryAndAuthor(category, userService.getUserById(userId).get());
        }
        return new ArrayList<>();
    }

    @GetMapping("/posts/keyWordsSearch")
    public List<Post> getPostsByTitleLikeOrContentLike(String keyWords){
        Set<Post> postSet = new HashSet<>();
        for (String keyWord:keyWords.split(",")) {
            postSet.addAll(postService.getPostsByTitleLikeOrContentLike(keyWord));
        }
        List<Post> filteredList = new ArrayList<>();
        filteredList.addAll(postSet);
        return filteredList;
    }

    @GetMapping("/posts/stats")
    public String getStats(){
        return postService.getPostsStats();
    }

    @GetMapping("/")
    public String home() {
        return "Hello in homepage";
    }

    @PostMapping("/user/register")
    public void registerUser(
            @RequestParam("email") String email,
            @RequestParam("password") String password
    ) {
        User user = new User(email, password, LocalDateTime.now(), false);
        userService.registerUser(user);
    }


    @PutMapping("/user/registerConfirm")
    public void registerConfirm(
            @RequestParam("userId") int userId
    ) {
        userService.activateUser(userId);
    }

    @DeleteMapping("/user/delete")
    public void deleteUserById(
            @RequestParam("userId") int userId
    ) {
        try {
            userService.deleteUser(userId);
        } catch (EmptyResultDataAccessException e) {
        }
    }

    @GetMapping("/users")
    public List<User> getAllUsers() {
        return userService.getAllUsersOrderByRegistrationDateTimeDesc();
    }

    @GetMapping("/user/email={email}")
    public User getUserById(
            @RequestParam("email") String email
    ) {
        return userService.getUserEmail(email).orElse(new User());
    }

    @PostMapping("/post/addPost")
    public void addPost(
            @RequestParam("title") String title,
            @RequestParam("content") String content,
            @RequestParam("category") Category category,
            @RequestParam("userId") int userId
    ) {
        Optional<User> userOptional = userService.getUserById(userId);
        if (userOptional.isPresent()) {
            if (userOptional.get().isStatus()) {
                postService.addPost(title, content, category, userOptional.get());
            }
        }
    }

    @GetMapping("/posts")
    public List<Post> getAllPosts(){
        return postService.getAllPosts();
    }

    @DeleteMapping("/post/delete")
    public void deletePostById(
            @RequestParam("postId") int postId
    ) {
        try {
            postService.deletePost(postId);
        } catch (EmptyResultDataAccessException e) {
        }
    }
}
