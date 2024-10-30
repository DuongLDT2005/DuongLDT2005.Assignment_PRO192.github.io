package com.example.assignment.model.schedule;

import java.util.List;

import com.example.assignment.model.user.Manager;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;

@Entity
public class Schedule {

    @Id
    private Long id;

    @OneToOne
    @JoinColumn(name = "manager_id", nullable = false)
    private Manager manager;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "singleDaySchedule_id", referencedColumnName = "id")
    private List<SingleDaySchedule> singleDaySchedule;

    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Manager getManager() {
        return manager;
    }

    public void setManager(Manager manager) {
        this.manager = manager;
    }

    public List<SingleDaySchedule> getSingleDaySchedule() {
        return singleDaySchedule;
    }
    

}
