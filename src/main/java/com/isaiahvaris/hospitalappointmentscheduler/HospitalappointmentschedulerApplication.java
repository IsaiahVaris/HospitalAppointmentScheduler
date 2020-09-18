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
    }

}
