package com.example.FitnessTracker.Controller;

import com.example.FitnessTracker.Entity.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthController {


    @GetMapping("/auth")
    public String authPage(Model model) {
        model.addAttribute("user", new User());
        return "auth";
    }
}
