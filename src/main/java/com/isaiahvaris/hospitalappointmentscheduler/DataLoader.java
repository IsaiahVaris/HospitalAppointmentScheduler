package com.isaiahvaris.hospitalappointmentscheduler;

import com.isaiahvaris.hospitalappointmentscheduler.models.Doctor;
import com.isaiahvaris.hospitalappointmentscheduler.service.DoctorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

@Slf4j
@Component
public class DataLoader implements ApplicationListener<ContextRefreshedEvent> {
    DoctorService doctorService;

    public DataLoader(DoctorService doctorService) {
        this.doctorService = doctorService;
    }

    @Transactional
    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        Doctor person = doctorService.getDoctorByEmail("admin@hospital.com");
        if (person == null) {
            person = new Doctor();
            person.setFirstName("Admin");
            person.setLastName("Admin");
            person.setEmail("admin@hospital.com");
            person.setPassword("admin");
            doctorService.addDoctor(person);
        }
    }

}
