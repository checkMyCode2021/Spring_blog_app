package com.example.spring_blog_app.service;

import com.example.spring_blog_app.model.User;
import com.example.spring_blog_app.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    public void registerUser(User user){
        userRepository.save(user);
    }

    public void activateUser(int userId){
        Optional<User> userOptional =  userRepository.findById(userId);
        if (userOptional.isPresent()){
            User user = userOptional.get();
            user.setStatus(true);
            userRepository.save(user);
        }
    }

    public void deleteUser(int userId){
        userRepository.deleteById(userId);
    }

    public List<User> getAllUsersOrderByRegistrationDateTimeDesc(){
        return userRepository.findAll(Sort.by(Sort.Direction.DESC,"registrationDateTime"));
    }

    public Optional<User> getUserEmail(String email){
        return userRepository.findAllByEmail(email);
    }
}
