package com.example.FitnessTracker.Config;

import com.google.genai.Client;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Geminiconfig {

    @Bean
    public Client client() {
        return new Client();
    }
}
