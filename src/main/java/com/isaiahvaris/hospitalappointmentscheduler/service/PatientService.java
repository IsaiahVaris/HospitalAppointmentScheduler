package com.isaiahvaris.hospitalappointmentscheduler.service;

import com.isaiahvaris.hospitalappointmentscheduler.models.Patient;
import org.springframework.stereotype.Service;

@Service
public interface PatientService {
    void addPatient (Patient patient);
    Patient getPatientById(long id);
    Patient getPatientByEmail(String email);
    Patient getPatientByEmailandPassWord(String email, String password);
    void deletePatient (Patient patient);
}
