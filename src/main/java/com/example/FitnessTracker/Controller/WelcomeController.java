package com.example.FitnessTracker.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WelcomeController {

    @GetMapping("/")
    public String homeRedirect() {
        return "redirect:/welcome2";
    }
    @GetMapping("/welcome2")
    public String welcome() {
        return "welcome2";
    }

}
