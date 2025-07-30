package com.example.FitnessTracker.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WelcomeController {

    @GetMapping("/")
    public String homeRedirect() {
        return "redirect:/welcome";
    }
    @GetMapping("/welcome")
    public String welcome() {
        return "welcome";
    }

}
