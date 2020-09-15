package com.isaiahvaris.hospitalappointmentscheduler.repositories;

import com.isaiahvaris.hospitalappointmentscheduler.models.Patient;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface PatientRepository extends CrudRepository<Patient, Long> {
    Patient findByEmail(String email);
    Patient findByEmailAndPassword(String email, String password);
}
