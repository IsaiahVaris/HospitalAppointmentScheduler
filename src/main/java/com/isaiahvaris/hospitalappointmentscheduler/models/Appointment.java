package com.isaiahvaris.hospitalappointmentscheduler.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String status;
    private Date appointmentTime;

    @ManyToOne
    private Patient patient;
    @ManyToOne
    private Doctor doctor;
}
