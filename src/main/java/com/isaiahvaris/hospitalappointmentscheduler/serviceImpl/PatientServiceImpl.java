package com.isaiahvaris.hospitalappointmentscheduler.serviceImpl;

import com.isaiahvaris.hospitalappointmentscheduler.models.Patient;
import com.isaiahvaris.hospitalappointmentscheduler.repositories.PatientRepository;
import com.isaiahvaris.hospitalappointmentscheduler.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PatientServiceImpl implements PatientService {
    @Autowired
    PatientRepository patientRepository;

    @Override
    public void addPatient(Patient patient) {
        patientRepository.save(patient);
    }

    @Override
    public Patient getPatientById(long id) {
        return patientRepository.findById(id).orElse(null);
    }

    @Override
    public Patient getPatientByEmail(String email) {
        return patientRepository.findByEmail(email);
    }

    @Override
    public Patient getPatientByEmailandPassWord(String email, String password) {

        return patientRepository.findByEmailAndPassword(email, password);
    }

    @Override
    public void deletePatient(Patient patient) {
         patientRepository.delete(patient);
    }
}
