package com.example.FitnessTracker.Controller;

import com.example.FitnessTracker.Entity.Exercises;
import com.example.FitnessTracker.Repository.ExercisesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/exercises")
public class ExerciseController {

    @Autowired
    private ExercisesRepository exerciseRepository;

    @GetMapping("/health")
    public String health() {
        return "health ok";
    }

    @GetMapping("/names")
    public List<String> getExerciseName() {

        List<String> exe = exerciseRepository.findAll().stream().map(Exercises::getName).collect(Collectors.toList());
        return exe;
    }

}
