package com.example.spring_blog_app.repositories;

import com.example.spring_blog_app.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findAllByEmail(String email);          // SELECT * FROM user WHERE email = ?;
}
