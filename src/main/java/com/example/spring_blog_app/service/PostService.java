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

    public void deletePost(int postId){
        postRepository.deleteById(postId);
    }

    public List<Post> getAllPosts(){
       return postRepository.findAll(Sort.by(Sort.Direction.DESC,"dateAdded"));
    }

    public List<Post> getPostsByCategory(Category category){
        return postRepository.findAllByCategory(category,Sort.by(Sort.Direction.DESC,"dateAdded"));
    }

    public List<Post> getPostsByCategoryAndAuthor(Category category,User author){
        return postRepository.findAllByCategoryAndAuthor(category,author,Sort.by(Sort.Direction.DESC,"dateAdded"));
    }

    public List<Post> getPostsByTitleLikeOrContentLike(String keyWord){
        return postRepository.findAllByTitleLikeOrContentLike("%" + keyWord + "%","%" + keyWord + "%" );
    }

    public String getPostsStats(){
        String output = "{\n";
                for(Object[] row: postRepository.postStatistics()){
                    output += "\"" + Category.values()[(int) row[0]].getCategoryName() +"\" : " + row[1] + "\n";
                }
                output += "}";
                return output;
    }
}
