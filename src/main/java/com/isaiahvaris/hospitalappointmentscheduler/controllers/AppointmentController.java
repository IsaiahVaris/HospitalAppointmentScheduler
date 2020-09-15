package com.isaiahvaris.hospitalappointmentscheduler.controllers;

import com.isaiahvaris.hospitalappointmentscheduler.models.Appointment;
import com.isaiahvaris.hospitalappointmentscheduler.models.Doctor;
import com.isaiahvaris.hospitalappointmentscheduler.models.Patient;
import com.isaiahvaris.hospitalappointmentscheduler.service.AppointmentService;
import org.apache.tomcat.jni.Local;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.HashMap;

@Controller
@RequestMapping("/appointment")
public class AppointmentController {
    @Autowired
    AppointmentService appointmentService;

    @PostMapping("/new")
    public String newAppointment(HttpSession session, @Valid Appointment appointment, Model model) {
        Object patientObj = session.getAttribute("patient");
        if (patientObj == null) return "landing";

        appointment.setPatient((Patient) patientObj);
        appointment.setStatus("pending");
        appointmentService.addAppointment(appointment);

        model.addAttribute("patient", patientObj);
        model.addAttribute("status", "pending");
        model.addAttribute("appointments", appointmentService.getAllPatientAppointmentsForStatus((Patient)patientObj, "pending"));
        model.addAttribute("appointmentrequest", new Appointment());
        model.addAttribute("requestsuccessful", "Appointment requested successfully! A doctor will handle your appointment.");

        return "patienthome";
    }

    @PostMapping("/set/{id}")
    public String setAppointment(@RequestParam HashMap<String, String> formValue, HttpSession session, @Valid Appointment appointment, @PathVariable("id") long id, Model model) {
        Object doctorObj = session.getAttribute("doctor");

        if (doctorObj == null)
        return "landing";

        String str = formValue.get("setdate").replace("T", " ");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime dateTime = LocalDateTime.parse(str, formatter);


        Appointment actual = appointmentService.getAppointmentById(id);
        actual.setDoctor((Doctor) doctorObj);
        actual.setStatus("accepted");
        System.out.println(appointment.getAppointmentTime());
        actual.setAppointmentTime(dateTime);

        appointmentService.updateAppointment(actual);

        model.addAttribute("doctor", doctorObj);

        model.addAttribute("status", "pending");
        model.addAttribute("appointments", appointmentService.getAllAppointmentsForStatus("pending"));
        model.addAttribute("appointmentrequest", new Appointment());
        model.addAttribute("requestsuccessful", "Appointment set successfully!");

        return "doctorhome";


    }


    @PostMapping("/cancel")
    public String cancelAppointment(HttpSession session, @Valid Appointment appointment, Model model) {
        Object patientObj = session.getAttribute("patient");
        Object doctorObj = session.getAttribute("doctor");

        if (patientObj != null) {
            appointment.setPatient((Patient) patientObj);
            appointment.setStatus("cancelled");

            appointmentService.updateAppointment(appointment);

            model.addAttribute("patient", patientObj);

            model.addAttribute("status", "pending");


            model.addAttribute("appointments", appointmentService.getAllPatientAppointmentsForStatus((Patient)patientObj, "cancelled"));
            model.addAttribute("appointmentrequest", new Appointment());
            model.addAttribute("requestsuccessful", "Appointment cancelled successfully!");

            return "patienthome";
        }

        if (doctorObj != null) {
            appointment.setDoctor((Doctor) doctorObj);
            appointment.setStatus("cancelled");

            appointmentService.updateAppointment(appointment);

            model.addAttribute("doctor", doctorObj);

            model.addAttribute("status", "pending");

            model.addAttribute("appointments", appointmentService.getAllDoctorAppointmentsForStatus((Doctor) doctorObj, "cancelled"));
            model.addAttribute("appointmentrequest", new Appointment());
            model.addAttribute("requestsuccessful", "Appointment cancelled successfully!");

            return "doctorhome";
        }



        return "landing";
    }

    @GetMapping("/{status}")
    public String viewAppointments(HttpSession session, Model model, @PathVariable("status") String status) {
        Object patientObj = session.getAttribute("patient");
        Object doctorObj = session.getAttribute("doctor");

        if (patientObj != null) {


//        appointment.setPatient((Patient) patientObj);
//        appointment.setStatus("Pending");
//        appointmentService.addAppointment(appointment);

            model.addAttribute("patient", patientObj);
            model.addAttribute("status", status);
            model.addAttribute("appointments", appointmentService.getAllPatientAppointmentsForStatus((Patient)patientObj, status));
            model.addAttribute("appointmentrequest", new Appointment());
//        model.addAttribute("requestsuccessful", "Appointment requested successfully! A doctor will handle your appointment.");


            return "patienthome";
        }

        if (doctorObj != null) {

            model.addAttribute("doctor", doctorObj);

            model.addAttribute("status", status);

            if (status.equals("pending")) {
                model.addAttribute("appointments", appointmentService.getAllAppointmentsForStatus("pending"));
            } else {
                model.addAttribute("appointments", appointmentService.getAllDoctorAppointmentsForStatus((Doctor) doctorObj, status));
            }
            model.addAttribute("appointmentrequest", new Appointment());
//            model.addAttribute("requestsuccessful", "Appointment cancelled successfully!");

            return "doctorhome";
        }


        return "landing";
    }


}
