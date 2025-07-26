package com.example.FitnessTracker.Services;

import com.example.FitnessTracker.Entity.WorkoutSession;
import com.example.FitnessTracker.Repository.WorkoutSessionRepo;
import com.fasterxml.jackson.datatype.jdk8.OptionalDoubleSerializer;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;

@Component
public class WorkoutSessionServices {

    @Autowired
    private WorkoutSessionRepo workoutSessionRepo;

    public List<WorkoutSession> getAllWorkoutSessions() {
        return workoutSessionRepo.findAll();
    }

    public void saveWorkoutSession(WorkoutSession workoutSession) {
        workoutSessionRepo.save(workoutSession);
    }

    public Optional<WorkoutSession> findById(@PathVariable ObjectId id) {
        return workoutSessionRepo.findById(id);
    }

}
