package com.example.FitnessTracker.Config;

import com.google.genai.Client;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.Optional;

//@Configuration
//public class Geminiconfig {
//
//
//    @Bean
//    public Client client() {
//        return new Client();
//    }
//
//
//
//}

@Configuration
@Profile("!test") // Don't load in test profile
public class Geminiconfig {

    @Value("${google.api.key}")
    private String apiKey;

    @Bean
    public Client client() {
        return new Client.Builder()
                .apiKey(apiKey)
                .build();
    }
}

