package com.example.assignment.model.schedule.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.assignment.model.schedule.Schedule;
import com.example.assignment.model.schedule.ScheduleRepository;
import com.example.assignment.model.schedule.SingleDaySchedule;
import com.example.assignment.model.user.Manager;

@Service
public class ScheduleService {
    private final ScheduleRepository scheduleRepository;

    @Autowired
    public ScheduleService(ScheduleRepository scheduleRepository){
        this.scheduleRepository=scheduleRepository;
    }

    public List<Schedule> getAllSchedule(){
        return scheduleRepository.findAll();
    }

    public Schedule getScheduleByManager(Manager manager){
        return scheduleRepository.findByManager(manager);
    }

    public void addSingleStaffSchedule(Manager manager,SingleDaySchedule singleDaySchedule){
        Schedule schedule = getScheduleByManager(manager);
        schedule.getSingleDaySchedule().add(singleDaySchedule);
        scheduleRepository.save(schedule);
    }
}
