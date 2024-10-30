package com.example.assignment.model.user;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository
        extends JpaRepository<User, Long> {
                List<User> findAllByShopName(String shopnName);
                List<User> findAllByRole(String role);
                List<User> findAllByUsername(String username);
                List<User> findAllByPhonenumber(String phonenumber);
                List<User> findAllByEmail(String email);
                
}
