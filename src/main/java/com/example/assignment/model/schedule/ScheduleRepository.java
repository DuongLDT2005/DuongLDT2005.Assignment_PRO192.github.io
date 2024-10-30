package com.example.assignment.model.schedule;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.assignment.model.user.Manager;

public interface ScheduleRepository
        extends JpaRepository<Schedule, Long> {

    Schedule findByManager(Manager manager);
}
