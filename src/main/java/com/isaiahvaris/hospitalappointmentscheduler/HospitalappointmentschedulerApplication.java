package com.isaiahvaris.hospitalappointmentscheduler;

import com.isaiahvaris.hospitalappointmentscheduler.models.Appointment;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.sql.Date;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@SpringBootApplication
public class HospitalappointmentschedulerApplication {

    public static void main(String[] args) {
        SpringApplication.run(HospitalappointmentschedulerApplication.class, args);
//
        Appointment appointmentss = new Appointment();
        appointmentss.setAppointmentTime(LocalDateTime.now());

//        LocalDateTime now = 2020-09-15T22:43;

        System.out.println(appointmentss.getAppointmentTime().format(DateTimeFormatter.ofPattern("dd-MM-yyyy hh:mm a")));
    }

}
