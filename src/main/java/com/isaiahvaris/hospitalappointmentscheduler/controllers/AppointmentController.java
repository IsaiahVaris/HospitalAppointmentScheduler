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
import java.util.List;

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
        Patient patient = actual.getPatient();

        List<Appointment> patientUpcomingAppointments = appointmentService.getAllPatientAppointmentsForStatus(patient, "accepted");
        List<Appointment> doctorUpcomingAppointments = appointmentService.getAllDoctorAppointmentsForStatus((Doctor) doctorObj, "accepted");

        for (Appointment appt : patientUpcomingAppointments) {
            if (appt.getAppointmentTime().isAfter(dateTime.minusHours(1))
                    && appt.getAppointmentTime().isBefore(dateTime.plusHours(1))) {
                model.addAttribute("timeoverlap",
                        "Patient has another appointment close to this time at " +
                        appt.getAppointmentTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm a"))
                                + " Set appointment at least one hour before or after this time.");

                model.addAttribute("mintime", LocalDateTime.now().plusMinutes(30)
                        .format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm")));model.addAttribute("doctor", doctorObj);
                model.addAttribute("status", "pending");
                model.addAttribute("appointments", appointmentService.getAllAppointmentsForStatus("pending"));
                model.addAttribute("appointmentrequest", new Appointment());

                return "doctorhome";
            }
        }

        for (Appointment appt : doctorUpcomingAppointments) {
            if (appt.getAppointmentTime().isAfter(dateTime.minusHours(1))
                    && appt.getAppointmentTime().isBefore(dateTime.plusHours(1))) {
                model.addAttribute("timeoverlap",
                        "You have another appointment close to this time at " +
                                appt.getAppointmentTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm a"))
                        + " Set appointment at least one hour before or after this time.");
                model.addAttribute("mintime", LocalDateTime.now().plusMinutes(30)
                        .format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm")));model.addAttribute("doctor", doctorObj);
                model.addAttribute("status", "pending");
                model.addAttribute("appointments", appointmentService.getAllAppointmentsForStatus("pending"));
                model.addAttribute("appointmentrequest", new Appointment());

                return "doctorhome";
            }
        }
        actual.setDoctor((Doctor) doctorObj);
        actual.setStatus("accepted");
        System.out.println(appointment.getAppointmentTime());
        actual.setAppointmentTime(dateTime);

        appointmentService.updateAppointment(actual);

        model.addAttribute("mintime", LocalDateTime.now().plusMinutes(30)
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm")));model.addAttribute("doctor", doctorObj);
        model.addAttribute("status", "pending");
        model.addAttribute("appointments", appointmentService.getAllAppointmentsForStatus("pending"));
        model.addAttribute("appointmentrequest", new Appointment());
        model.addAttribute("requestsuccessful", "Appointment set successfully!");

        return "doctorhome";
    }

    @PostMapping("/delete/{id}")
    public String deleteAppointment(HttpSession session, @PathVariable("id") long id, Model model) {
        Object patientObj = session.getAttribute("patient");

        if (patientObj == null)
        return "landing";

        appointmentService.deleteAppointment(appointmentService.getAppointmentById(id));

        model.addAttribute("patient", patientObj);
        model.addAttribute("status", "cancelled");
        model.addAttribute("appointments", appointmentService.getAllPatientAppointmentsForStatus((Patient)patientObj, "cancelled"));
        model.addAttribute("appointmentrequest", new Appointment());
        model.addAttribute("requestsuccessful", "Appointment deleted successfully!");

        return "patienthome";
    }


    @PostMapping("/cancel/{id}")
    public String cancelAppointment(HttpSession session, @PathVariable("id") long id, Model model) {
        Object patientObj = session.getAttribute("patient");

        if (patientObj == null)
        return "landing";

        Appointment appointment = appointmentService.getAppointmentById(id);
        appointment.setStatus("cancelled");

        appointmentService.updateAppointment(appointment);

        model.addAttribute("patient", patientObj);
        model.addAttribute("status", "cancelled");
        model.addAttribute("appointments", appointmentService.getAllPatientAppointmentsForStatus((Patient)patientObj, "cancelled"));
        model.addAttribute("appointmentrequest", new Appointment());
        model.addAttribute("requestsuccessful", "Appointment cancelled successfully!");

        return "patienthome";
    }

    @GetMapping("/{status}")
    public String viewAppointments(HttpSession session, Model model, @PathVariable("status") String status) {

        for (Appointment appt : appointmentService.getAllAppointmentsForStatus("accepted")) {
            if (appt.getAppointmentTime().isBefore(LocalDateTime.now().minusHours(1))) {
                appt.setStatus("completed");
                appointmentService.updateAppointment(appt);
            }
        }
        Object patientObj = session.getAttribute("patient");
        Object doctorObj = session.getAttribute("doctor");

        if (patientObj != null) {
            model.addAttribute("patient", patientObj);
            model.addAttribute("status", status);
            model.addAttribute("appointments", appointmentService.getAllPatientAppointmentsForStatus((Patient)patientObj, status));
            model.addAttribute("appointmentrequest", new Appointment());

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
            model.addAttribute("mintime", LocalDateTime.now().plusMinutes(30)
                    .format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm")));
            model.addAttribute("appointmentrequest", new Appointment());
            return "doctorhome";
        }
        return "landing";
    }
}