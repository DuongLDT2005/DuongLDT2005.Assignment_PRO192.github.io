package com.example.student_demo.model.order;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.example.student_demo.model.user.User;

public class Order {
    private String id;
    private List<OrderItem> orderItems;
    private LocalDate orderDate;
    private double totalBill;
    private String status;//in-process, proccessed, canceled
    private User employee;

    public Order(User employee, LocalDate orderDate, String status, double totalBill) {
        this.employee = employee;
        this.orderDate = orderDate;
        this.orderItems = new ArrayList<>();
        this.status = status;
        this.totalBill = totalBill;
    }

    public Order(User employee, String id, LocalDate orderDate, OrderItem orderItems, String status, double totalBill) {
        this.employee = employee;
        this.id = id;
        this.orderDate = orderDate;
        this.orderItems = new ArrayList<>();
        this.status = status;
        this.totalBill = totalBill;
    }

    public Order() {
        this.orderItems=new ArrayList<>();
    }

    public String getId() {
        return id;
    }
  
    public LocalDate getOrderDate() {
        return orderDate;
    }
    public double getTotalBill() {
        return totalBill;
    }
    public String getStatus() {
        return status;
    }
    public User getEmployee() {
        return employee;
    }
    public void setId(String id) {
        this.id = id;
    }
   
    public void setOrderDate(LocalDate orderDate) {
        this.orderDate = orderDate;
    }
    public void setTotalBill(double totalBill) {
        this.totalBill = totalBill;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public void setEmployee(User employee) {
        this.employee = employee;
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }
    
    
}
