package com.isaiahvaris.hospitalappointmentscheduler.service;

import com.isaiahvaris.hospitalappointmentscheduler.models.Appointment;
import com.isaiahvaris.hospitalappointmentscheduler.models.Doctor;
import com.isaiahvaris.hospitalappointmentscheduler.models.Patient;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public interface AppointmentService {
    Appointment addAppointment(Appointment appointment);

    Appointment getAppointmentByPatientAndDate(Patient patient, Date date);
    Appointment getAppointmentByDoctorAndDate(Doctor doctor, Date date);

    Appointment updateAppointment(Appointment appointment);

    List<Appointment> getAllPatientAppointments(Patient patient);
    List<Appointment> getAllDoctorAppointments(Doctor doctor);

    Appointment cancelAppointment(Appointment appointment);

    void deleteAppointment(Appointment appointment);



}
