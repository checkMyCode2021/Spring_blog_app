package com.example.spring_blog_app.service;

import com.example.spring_blog_app.configuration.EncoderAlgorithm;
import com.example.spring_blog_app.model.Role;
import com.example.spring_blog_app.model.User;
import com.example.spring_blog_app.repositories.RoleRepository;
import com.example.spring_blog_app.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;
    @Autowired
    EncoderAlgorithm encoderAlgorithm;
    @Autowired
    RoleRepository roleRepository;

    public UserDetails getCredential(Authentication auth){
        return auth != null ? (UserDetails) auth.getPrincipal() :null;
    }

    public void registerUser(User user){
        Set<Role> roles = new HashSet<>();
        roles.add(roleRepository.getById(1));  // default role is USER with ID = 1
        user.setRoles(roles);
        user.setPassword(encoderAlgorithm.getPasswordEncoder().encode(user.getPassword()));
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

    public Optional<User> getUserByEmail(String email){
        return userRepository.findAllByEmail(email);
    }

    public Optional<User> getUserById(int userId){
        return userRepository.findById(userId);
    }
}
