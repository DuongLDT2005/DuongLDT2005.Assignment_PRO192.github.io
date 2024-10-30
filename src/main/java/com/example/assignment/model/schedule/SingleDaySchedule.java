package com.example.assignment.model.schedule;

import java.time.LocalDate;

import com.example.assignment.model.user.Staff;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;


@Entity
public class SingleDaySchedule {
    @Id
    private Long id;


    @OneToOne
    @JoinColumn(name="staff_id",nullable=false)
    private Staff staff;
    private LocalDate checkInDate;
    private LocalDate checkOuDate;
    public SingleDaySchedule(Staff staff, LocalDate checkInDate, LocalDate checkOuDate) {
        this.staff = staff;
        this.checkInDate = checkInDate;
        this.checkOuDate = checkOuDate;
    }
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Staff getStaff() {
        return staff;
    }
    public void setStaff(Staff staff) {
        this.staff = staff;
    }
    public LocalDate getCheckInDate() {
        return checkInDate;
    }
    public void setCheckInDate(LocalDate checkInDate) {
        this.checkInDate = checkInDate;
    }
    public LocalDate getCheckOuDate() {
        return checkOuDate;
    }
    public void setCheckOuDate(LocalDate checkOuDate) {
        this.checkOuDate = checkOuDate;
    }

    
    
}
