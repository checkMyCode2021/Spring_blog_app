package com.example.spring_blog_app.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PostDto {
    @NotBlank(message = "Title must be not empty")
    @Size(min = 5, max = 100, message = "Title must have number of characters between {min} to {max}")
    private String title;
    @NotBlank(message = "Content must be not empty")
    @Size(min = 10, max = 5000, message = "Message must have number of characters between {min} to {max}")
    private String content;
//    @NotBlank(message = "Category must be not empty")
    private Category category;

}
