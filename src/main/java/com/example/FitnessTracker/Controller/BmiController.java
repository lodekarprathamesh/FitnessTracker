package com.example.FitnessTracker.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BmiController {
    @GetMapping("/bmi")
    public String bmi(){
        return "bmi";
    }

    @GetMapping("/bmiNotlogin")
    public String bmiNotlogin(){
        return "bmiNotlogin";
    }
}
