package com.example.spring_blog_app.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Locale;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int postId;
    private String title;
    @Type(type = "text")
    private String content;
    private LocalDateTime dateAdded;
    private Category category;
    @ManyToOne(
            fetch = FetchType.EAGER
    )
    private User author;
}
