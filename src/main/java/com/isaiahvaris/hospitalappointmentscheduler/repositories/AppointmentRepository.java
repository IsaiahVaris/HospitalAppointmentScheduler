package com.isaiahvaris.hospitalappointmentscheduler.repositories;

import com.isaiahvaris.hospitalappointmentscheduler.models.Appointment;
import com.isaiahvaris.hospitalappointmentscheduler.models.Doctor;
import com.isaiahvaris.hospitalappointmentscheduler.models.Patient;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface AppointmentRepository extends CrudRepository<Appointment, Long> {
    List<Appointment> findAllByPatientAndStatus(Patient patient, String status);
    List<Appointment> findAllByStatus(String status);
    List<Appointment> findAllByDoctorAndStatus(Doctor doctor, String status);
    Appointment findByPatientAndAppointmentTime(Patient patient, Date appointmentTime);
    Appointment findByDoctorAndAppointmentTime(Doctor doctor, Date appointmentTime);

}
