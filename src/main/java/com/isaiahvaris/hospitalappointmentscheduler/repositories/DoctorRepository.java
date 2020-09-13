package com.isaiahvaris.hospitalappointmentscheduler.repositories;

import com.isaiahvaris.hospitalappointmentscheduler.models.Doctor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DoctorRepository extends CrudRepository<Doctor, Long> {
    Doctor findByEmail(String email);
    Doctor findByEmailAndPassWord(String email, String password);
}
