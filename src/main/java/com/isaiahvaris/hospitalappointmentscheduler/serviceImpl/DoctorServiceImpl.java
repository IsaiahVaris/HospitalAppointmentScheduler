package com.isaiahvaris.hospitalappointmentscheduler.serviceImpl;

import com.isaiahvaris.hospitalappointmentscheduler.models.Doctor;
import com.isaiahvaris.hospitalappointmentscheduler.repositories.DoctorRepository;
import com.isaiahvaris.hospitalappointmentscheduler.service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DoctorServiceImpl implements DoctorService {
    @Autowired
    DoctorRepository doctorRepository;

    @Override
    public void addDoctor(Doctor doctor) {
        doctorRepository.save(doctor);
    }

    @Override
    public Doctor getDoctorById(long id) {
        return doctorRepository.findById(id).orElse(null);
    }

    @Override
    public Doctor getDoctorByEmail(String email) {
        return doctorRepository.findByEmail(email);
    }

    @Override
    public Doctor getDoctorByEmailandPassWord(String email, String password) {
        return doctorRepository.findByEmailAndPassword(email, password);
    }

    @Override
    public void deleteUser(Doctor doctor) {
        doctorRepository.delete(doctor);
    }
}
