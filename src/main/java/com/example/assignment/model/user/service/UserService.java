package com.example.assignment.model.user.service;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.assignment.model.user.User;
import com.example.assignment.model.user.UserRepository;

@Service
public class UserService{
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    
    public List<User> getUser(){
        return userRepository.findAll();
    }

    public void addUser(User user){
        userRepository.save(user);
    }

    public User updateUser(User user) {
        // Reattaches the detached entity and updates it in the database
        return userRepository.save(user);  // save() works as an upsert (insert if new, update if exists)
    }

}