package com.isaiahvaris.hospitalappointmentscheduler.controllers;

import com.isaiahvaris.hospitalappointmentscheduler.models.Appointment;
import com.isaiahvaris.hospitalappointmentscheduler.models.Doctor;
import com.isaiahvaris.hospitalappointmentscheduler.models.Patient;
import com.isaiahvaris.hospitalappointmentscheduler.service.AppointmentService;
import com.isaiahvaris.hospitalappointmentscheduler.service.DoctorService;
import com.isaiahvaris.hospitalappointmentscheduler.service.PatientService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Controller
@RequestMapping("/")
public class IndexController {
    AppointmentService appointmentService;
    PatientService patientService;
    DoctorService doctorService;

    public IndexController(AppointmentService appointmentService, PatientService patientService, DoctorService doctorService) {
        this.appointmentService = appointmentService;
        this.patientService = patientService;
        this.doctorService = doctorService;
    }

    @GetMapping("/")
    public String index(Model model, HttpSession session) {

        Object patientObj = session.getAttribute("patient");
        Object doctorObj = session.getAttribute("doctor");

        if (patientObj != null) {
            String status = "pending";

            model.addAttribute("patient", (Patient) patientObj);
            model.addAttribute("status", status);
            model.addAttribute("appointments", appointmentService.getAllPatientAppointmentsForStatus((Patient)patientObj, status));
            model.addAttribute("appointmentrequest", new Appointment());

            return "patienthome";
        }
        if (doctorObj != null) {
            Doctor gottenDoctor = (Doctor)doctorObj;
            if(gottenDoctor.getEmail().equals("admin@hospital.com")){
                session.setAttribute("doctor", gottenDoctor);
                model.addAttribute("doctor", new Doctor());
                return "admin";
            }

            String status = "pending";

            model.addAttribute("mintime", LocalDateTime.now().plusMinutes(30)
                    .format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm")));
            model.addAttribute("doctor", gottenDoctor);
            model.addAttribute("status", status);
            model.addAttribute("appointments", appointmentService.getAllAppointmentsForStatus(status));
            model.addAttribute("appointmentrequest", new Appointment());

            return "doctorhome";
        }

        return "landing";
    }


}
