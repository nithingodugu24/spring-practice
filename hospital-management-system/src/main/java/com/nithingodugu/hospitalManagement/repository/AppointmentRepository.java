package com.nithingodugu.hospitalManagement.repository;

import com.nithingodugu.hospitalManagement.entity.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
}