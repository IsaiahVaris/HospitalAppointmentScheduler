package com.isaiahvaris.hospitalappointmentscheduler.service;

import com.isaiahvaris.hospitalappointmentscheduler.models.Doctor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface DoctorService {
    void addDoctor (Doctor doctor);
    Doctor getDoctorById(long id);
    Doctor getDoctorByEmail(String email);
    List<Doctor> getAllDoctors();
    Doctor getDoctorByEmailandPassWord(String email, String password);
    void deleteUser(Doctor doctor);
}
