package com.example.spring_blog_app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@SpringBootApplication
public class SpringBlogAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBlogAppApplication.class, args);
    }

}
