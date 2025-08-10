package com.example.FitnessTracker.Services;

import com.google.genai.Client;
import com.google.genai.types.GenerateContentResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AskServices {

    private final Client client;

//    public String AskGemini(String prompt) {
//
//
//        GenerateContentResponse response =
//                client.models.generateContent(
//                        "gemini-2.5-flash",
//                        prompt,
//                        null);
//
//        return response.text();
//    }

    public String askGemini(String prompt) {
        try {
            GenerateContentResponse response = client.models.generateContent(
                    "gemini-2.5-flash",
                    prompt,
                    null
                    // If method requires options, provide them here; otherwise omit
            );

            return response.text();
        } catch (Exception e) {
            // Log error, handle as needed
            e.printStackTrace();
            return "Error generating content from AI.";
        }
    }
}
