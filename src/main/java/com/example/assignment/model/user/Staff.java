package com.example.assignment.model.user;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("Staff")
public class Staff extends User {

    @Column(nullable = true, name = "workingHours")
    private Integer workingHours;
    @Column(nullable = true, name = "wage")
    private Double wage;
    private Double salary;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;

    public Staff() {
    }

    public Staff(int workingHours, double wage) {
        this.workingHours = workingHours;
        this.wage = wage;
    }

    public Staff(double salary, String shopName, String email, String name, String password, String phonenumber, String role, boolean status, String username) {
        super(shopName, email, name, password, phonenumber, role, status, username);
        this.salary = salary;
    }
    

    public Staff(int workingHours, double wage, double salary, String shopName, String email, String name, String password, String phonenumber, String role, boolean status, String username) {
        super(shopName, email, name, password, phonenumber, role, status, username);
        this.salary = salary;
        this.workingHours = workingHours;
        this.wage = wage;
    }

    public Staff(String shopName, String email, String name, String password, String phonenumber, String role, boolean status, String username) {
        super(shopName, email, name, password, phonenumber, role, status, username);
    }

    public Integer getWorkingHours() {
        return workingHours;
    }

    public double getWage() {
        return wage;
    }

    public double getSalary() {
        return workingHours * wage;
    }

    public void setWorkingHours(int workingHours) {
        this.workingHours = workingHours;
    }

    public void setWage(double wage) {
        this.wage = wage;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public void setCheckInDate(LocalDate checkInDate) {
        this.checkInDate = checkInDate;
    }

    public void setCheckOutDate(LocalDate checkOutDate) {
        this.checkOutDate = checkOutDate;
    }

    public LocalDate getCheckInDate() {
        return checkInDate;
    }

    public LocalDate getCheckOutDate() {
        return checkOutDate;
    }

}
