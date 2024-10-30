package com.example.assignment.model.user.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.assignment.model.user.Manager;
import com.example.assignment.model.user.Staff;
import com.example.assignment.model.user.User;
import com.example.assignment.model.user.UserRepository;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getUser() {
        return userRepository.findAll();
    }

    public void addUser(User user) {
        userRepository.save(user);
        System.out.println(user);
    }

    public User updateUser(User user) {
        return userRepository.save(user);
    }

    public void removeUser(long id) {
        userRepository.deleteById(id);
    }

    public void findUser(long id) {
        userRepository.findById(id);
    }

    public String signIn(String shopName,
            String email,
            String name,
            String password,
            String phonenumber,
            String role,
            boolean status,
            String username) {

        if (userRepository.findAllByUsername(username) == null) {
        } else {
            return "nameExisted";
        }

        if (userRepository.findAllByPhonenumber(phonenumber) == null) {
        } else {
            return "phoneNumberExisted";
        }

        if (userRepository.findAllByEmail(email) == null) {
        } else {
            return "emailExisted";
        }

        if (role.equals("manager")) {
            addUser(new Manager(shopName, email, name, password, phonenumber, role, status, username)); 
        }else {
            addUser(new Staff(null, email, name, password, phonenumber, role, status, username));
        }
        return "checked";

    }

}
