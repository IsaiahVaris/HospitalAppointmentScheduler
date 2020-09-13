package com.isaiahvaris.hospitalappointmentscheduler.serviceImpl;

import com.isaiahvaris.hospitalappointmentscheduler.models.Appointment;
import com.isaiahvaris.hospitalappointmentscheduler.models.Doctor;
import com.isaiahvaris.hospitalappointmentscheduler.models.Patient;
import com.isaiahvaris.hospitalappointmentscheduler.repositories.AppointmentRepository;
import com.isaiahvaris.hospitalappointmentscheduler.service.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class AppointmentServiceImpl implements AppointmentService {
    @Autowired
    AppointmentRepository appointmentRepository;

    @Override
    public Appointment addAppointment(Appointment appointment) {
        return appointmentRepository.save(appointment);
    }

    @Override
    public Appointment updateAppointment(Appointment appointment) {
        return appointmentRepository.save(appointment);
    }

    @Override
    public Appointment cancelAppointment(Appointment appointment) {
        return appointmentRepository.save(appointment);
    }

    @Override
    public Appointment getAppointmentByPatientAndDate(Patient patient, Date date) {
        return appointmentRepository.findByPatientAndAppointmentTime(patient, date);
    }

    @Override
    public Appointment getAppointmentByDoctorAndDate(Doctor doctor, Date date) {
        return appointmentRepository.findByDoctorAndAppointmentTime(doctor, date);
    }

    @Override
    public List<Appointment> getAllPatientAppointments(Patient patient) {
        return appointmentRepository.findAllByPatient(patient);
    }

    @Override
    public List<Appointment> getAllDoctorAppointments(Doctor doctor) {
        return appointmentRepository.findAllByDoctor(doctor);
    }

    @Override
    public void deleteAppointment(Appointment appointment) {
        appointmentRepository.delete(appointment);
    }
}
