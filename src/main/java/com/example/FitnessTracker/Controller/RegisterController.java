package com.example.FitnessTracker.Controller;

import com.example.FitnessTracker.Entity.User;
import com.example.FitnessTracker.Repository.RegisterRepository;
import com.example.FitnessTracker.Services.RegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller//
//@RequestMapping("/api")
public class RegisterController {

    @Autowired
    private  RegisterRepository registerRepository;

    @Autowired
    private RegisterService registerService;



    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("user", new User());
        return "auth";
    }

    @PostMapping("/register/save")
    public String RegisterUser(@ModelAttribute("user") User user) {
        if(registerRepository.findByUsername(user.getUsername()) != null){
            return "redirect:/auth?error=Username already exists";
        }
        registerService.saveNewUser(user);
        return "redirect:/home";
    }

}
