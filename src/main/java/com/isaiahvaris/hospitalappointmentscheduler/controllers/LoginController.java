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

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
@RequestMapping("/login")
public class LoginController {
    PatientService patientService;
    DoctorService doctorService;

    public LoginController(PatientService patientService, DoctorService doctorService) {
        this.patientService = patientService;
        this.doctorService = doctorService;
    }

    @GetMapping("/patient")
    public String loginPatient(Model model) {

        model.addAttribute("patient", new Patient());
        model.addAttribute("invalid", null);
        model.addAttribute("newregistration", null);

        return "patientlogin";
    }

    @PostMapping("/patient")
    public String login (HttpSession session, @Valid Patient patient, Model model) {

        Patient gottenPatient = patientService.getPatientByEmail(patient.getEmail());

        if (gottenPatient == null) {
            //error message if email does not exist in database
            model.addAttribute("invalid", "Patient does not exist. Check login details or register.");
            return "patientlogin";
        }

        gottenPatient = patientService.getPatientByEmailandPassWord(patient.getEmail(), patient.getPassword());

        if (gottenPatient == null) {
            //error message if email exists but wrong password provided
            model.addAttribute("invalid", "Incorrect password");
            return "patientlogin";
        }
        session.setAttribute("patient", gottenPatient);
        return "redirect:/";
    }

    @GetMapping("/doctor")
    public String loginDoctor(Model model) {
        model.addAttribute("doctor", new Doctor());
        model.addAttribute("invalid", null);
        model.addAttribute("newregistration", null);

        return "doctorlogin";
    }

    @PostMapping("/doctor")
    public String login (HttpSession session, @Valid Doctor doctor, Model model) {
        Doctor gottenDoctor = doctorService.getDoctorByEmail(doctor.getEmail());

        if (gottenDoctor == null) {
            //error message if email does not exist in database
            model.addAttribute("invalid", "Doctor does not exist. Check login details or register.");
            return "doctorlogin";
        }

        gottenDoctor = doctorService.getDoctorByEmailandPassWord(doctor.getEmail(), doctor.getPassword());

        if (gottenDoctor == null) {
            //error message if email exists but wrong password provided
            model.addAttribute("invalid", "Incorrect password");
            return "doctorlogin";
        }
        session.setAttribute("doctor", gottenDoctor);
        return "redirect:/";
    }

    @GetMapping("/logout")
    public String logout(Model model, HttpSession session) {
        if (session != null) {
            session.invalidate();
        }
        return "landing";
    }
}
