package com.example.student_demo.model.user;

import java.time.LocalDate;

public class Staff extends User {

    private int workingHours;
    private double wage;
    private double salary;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;


    public Staff() {

    }

    public Staff(int workingHours, double wage) {
        this.workingHours = workingHours;
        this.wage = wage;
    }

    public int getWorkingHours() {
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
