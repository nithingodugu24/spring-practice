package com.nithingodugu.hospitalManagement;

import com.nithingodugu.hospitalManagement.entity.Appointment;
import com.nithingodugu.hospitalManagement.service.AppointmentService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

@SpringBootTest
public class AppointmentTests {

    @Autowired
    private AppointmentService appointmentService;


    @Test
    public void appointmentTest(){
        Appointment appointment = Appointment.builder()
                .reason("heart problem")
                .appointmentTime(LocalDateTime.now())
                .build();

//        appointmentService.createNewAppointment(appointment, 1L, 2L);
//
//        System.out.println(appointment);
    }
}
