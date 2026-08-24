package com.example.ai_resume_analyzer.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardController {

	 @GetMapping("/dashboard")
	    public String dashboard(Model model) {

	        model.addAttribute("email", "bhumiichauhan3009@gmail.com");

	        return "dashboard/dashboard";
	    }
}