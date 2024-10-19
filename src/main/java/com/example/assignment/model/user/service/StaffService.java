package com.example.assignment.model.user.service;

import java.time.Duration;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.assignment.model.product.service.ProductService;
import com.example.assignment.model.user.Staff;
import com.example.assignment.model.user.User;
import com.example.assignment.model.user.UserRepository;

@Service
public class StaffService {

    private final UserRepository userRepository;
    private ProductService productService;
    private final UserService userService;

    @Autowired
    public StaffService(UserRepository userRepository,UserService userService) {
        this.userRepository = userRepository;
        this.userService=userService;
    }

    public List<User> getStaff() {
        List<User> staffList= new ArrayList<>();
        for(User user: userService.getUser()){
            if(user.getRole().equals("staff")){
                staffList.add(user);
            }
        }
        return staffList;
    }

    public void setCheckInTime(Staff staff) {
        LocalDate time = LocalDate.now();
        staff.setCheckInDate(time);

    }

    public void setCheckOutTime(Staff staff) {
        LocalDate time = LocalDate.now();
        staff.setCheckInDate(time);

    }

    public void caculatWrokingOur(Staff staff) {
        if (staff.getCheckInDate() != null && staff.getCheckOutDate() != null) {
            Duration duration = Duration.between(staff.getCheckInDate(), staff.getCheckOutDate());
            int workedHours = (int) duration.toHours();
            int workingHours = (staff.getWorkingHours());
            staff.setWorkingHours(workingHours += workedHours);
        }

    }

    /*
        take order:
        - 
        -
     */
}
