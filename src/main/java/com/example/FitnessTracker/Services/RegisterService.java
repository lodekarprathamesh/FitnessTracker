package com.example.FitnessTracker.Services;

import com.example.FitnessTracker.Entity.User;
import com.example.FitnessTracker.Repository.RegisterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class RegisterService {

    @Autowired
    private RegisterRepository registerRepository;

    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public void saveNewUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(Arrays.asList("USER"));
        registerRepository.save(user);
    }
}
