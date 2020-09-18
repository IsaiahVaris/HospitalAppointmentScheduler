package com.isaiahvaris.hospitalappointmentscheduler.controllers;

import com.isaiahvaris.hospitalappointmentscheduler.models.Appointment;
import com.isaiahvaris.hospitalappointmentscheduler.models.Doctor;
import com.isaiahvaris.hospitalappointmentscheduler.models.Patient;
import com.isaiahvaris.hospitalappointmentscheduler.models.User;
import com.isaiahvaris.hospitalappointmentscheduler.service.DoctorService;
import com.isaiahvaris.hospitalappointmentscheduler.service.PatientService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Controller
@RequestMapping("/profile")
public class ProfileController {
    PatientService patientService;
    DoctorService doctorService;

    public ProfileController(PatientService patientService, DoctorService doctorService) {
        this.patientService = patientService;
        this.doctorService = doctorService;
    }

    @GetMapping("/")
    public String profile(Model model, HttpSession session) {

        Object patientObj = session.getAttribute("patient");
        Object doctorObj = session.getAttribute("doctor");

        if (patientObj != null) {
            model.addAttribute("user", (Patient) patientObj);
            return "profile";
        }
        if (doctorObj != null) {
            model.addAttribute("user", (Doctor) doctorObj);
            return "doctorprofile";
        }

        return "landing";
    }

    @PostMapping("/update/{field}")
    public String updateProfilePatient(Model model, HttpSession session, @PathVariable("field") String field, @Valid Patient user) {

        Object patientObj = session.getAttribute("patient");

        if (patientObj == null)
            return "landing";

        Patient patient = (Patient) patientObj;

        switch (field) {
            case "firstName":
                patient.setFirstName(user.getFirstName());
                field = "First name";
                break;
            case "lastName":
                patient.setLastName(user.getLastName());
                field = "Last name";
                break;
            case "email":
                patient.setEmail(user.getEmail());
                field = "Email";
                break;
            case "gender":
                patient.setGender(user.getGender());
                field = "Gender";
                break;
            case "dob":
                patient.setBirthDay(user.getBirthDay());
                field = "Date of birth";
                break;
            case "password":
                patient.setPassword(user.getPassword());
                field = "Password";
                break;
        }
        patientService.addPatient(patient);

        model.addAttribute("user", patient);

        model.addAttribute("update", field + " updated successfully!");
        return "profile";
    }

    @PostMapping("/doctor/update/{field}")
    public String updateProfileDoctor(Model model, HttpSession session, @PathVariable("field") String field, @Valid Doctor user) {

        Object doctorObj = session.getAttribute("doctor");

        if (doctorObj == null)
            return "landing";

            Doctor doctor = (Doctor) doctorObj;

            switch (field) {
                case "firstName":
                    doctor.setFirstName(user.getFirstName());
                    field = "First name";
                    break;
                case "lastName":
                    doctor.setLastName(user.getLastName());
                    field = "Last name";
                    break;
                case "email":
                    doctor.setEmail(user.getEmail());
                    field = "Email";
                    break;
                case "gender":
                    doctor.setGender(user.getGender());
                    field = "Gender";
                    break;
                case "dob":
                    doctor.setBirthDay(user.getBirthDay());
                    field = "Date of Birth";
                    break;
                case "password":
                    doctor.setPassword(user.getPassword());
                    field = "Password";
                    break;
            }
            doctorService.addDoctor(doctor);

            model.addAttribute("user", doctor);
            model.addAttribute("update", field + " updated successfully!");
            return "doctorprofile";
    }

    @GetMapping("/delete")
    public String deleteProfile(Model model, HttpSession session) {
        Object patientObj = session.getAttribute("patient");

        if (patientObj == null)
        return "landing";

        model.addAttribute("user", (Patient) patientObj);
        model.addAttribute("delete", "yes");
        return "profile";
    }

    @PostMapping("/delete")
    public String deleteProfilePatient(Model model, HttpSession session) {

        Object patientObj = session.getAttribute("patient");

        if (patientObj == null)
            return "landing";

        patientService.deletePatient((Patient) patientObj);
        return "redirect:/";
    }

    }
