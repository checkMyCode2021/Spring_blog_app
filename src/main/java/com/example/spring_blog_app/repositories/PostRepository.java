package com.example.spring_blog_app.repositories;

import com.example.spring_blog_app.model.Category;
import com.example.spring_blog_app.model.Post;
import com.example.spring_blog_app.model.User;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface PostRepository extends JpaRepository<Post,Integer> {

    List<Post> findAllByCategory(Category category, Sort sort);

    List<Post> findAllByCategoryAndAuthor(Category category, User author, Sort sort);

    List<Post> findAllByTitleLikeOrContentLike(String titlePattern,String contentPattern);

    @Query(
            value = "SELECT p.category, count(*) FROM Post p GROUP BY p.category ORDER BY 2 DESC",
            nativeQuery = true
    )
    List<Object[]> postStatistics();

}
