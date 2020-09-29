package com.isaiahvaris.hospitalappointmentscheduler.controllers;

import com.isaiahvaris.hospitalappointmentscheduler.models.Doctor;
import com.isaiahvaris.hospitalappointmentscheduler.service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    DoctorService doctorService;

    @GetMapping("/doctors")
    public String getDoctors(Model model, HttpSession session) {
        Object doctorObj = session.getAttribute("doctor");

        if (doctorObj == null)
            return "landing";

        model.addAttribute("doctor", new Doctor());
        model.addAttribute("doctors", doctorService.getAllDoctors());
        return "admin";
    }
}
