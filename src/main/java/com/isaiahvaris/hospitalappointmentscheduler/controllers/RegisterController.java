package com.isaiahvaris.hospitalappointmentscheduler.controllers;

import com.isaiahvaris.hospitalappointmentscheduler.models.Doctor;
import com.isaiahvaris.hospitalappointmentscheduler.models.Patient;
import com.isaiahvaris.hospitalappointmentscheduler.service.DoctorService;
import com.isaiahvaris.hospitalappointmentscheduler.service.PatientService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequestMapping("/register")
public class RegisterController {
    PatientService patientService;
    DoctorService doctorService;

    public RegisterController(PatientService patientService, DoctorService doctorService) {
        this.patientService = patientService;
        this.doctorService = doctorService;
    }

    @GetMapping("/patient")
    public String registerPatient(Model model) {

        model.addAttribute("invalid", null);
        model.addAttribute("patient", new Patient());
        return "patientregister";
    }

    @GetMapping("/doctor")
    public String registerDoctor(Model model) {

        model.addAttribute("invalid", null);
        model.addAttribute("doctor", new Doctor());
        return "doctorregister";
    }


    @PostMapping("/patient")
    public String signup(@Valid Patient patient, Model model) {

        Patient gottenPatient = patientService.getPatientByEmail(patient.getEmail());
        if (gottenPatient != null) {
            //error message if email provided is already registered
            model.addAttribute("invalid", "Patient already exists");
            return "patientregister";
        }

        patientService.addPatient(patient);
        //successful registration message
        model.addAttribute("newregistration", "Registration successful!");
        return "patientlogin";
    }

    @PostMapping("/doctor")
    public String signup(@Valid Doctor doctor, Model model) {

        Doctor gottenDoctor = doctorService.getDoctorByEmail(doctor.getEmail());
        if (gottenDoctor != null) {
            //error message if email provided is already registered
            model.addAttribute("invalid", "Patient already exists");
            return "doctorregister";
        }

        doctorService.addDoctor(doctor);
        //successful registration message
        model.addAttribute("newregistration", "Registration successful!");
        return "doctorlogin";
    }
}
