package com.example.FitnessTracker.Controller;

import com.example.FitnessTracker.Entity.Workout;
import com.example.FitnessTracker.Entity.WorkoutSession;
import com.example.FitnessTracker.Services.WorkoutSessionServices;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class FetchingWorkoutController {
    @Autowired
    WorkoutSessionServices workoutSessionServices;

    @GetMapping("getWorkout/{sessionId}")
    public Map<String, List<Workout>>  getWorkouts(@PathVariable String sessionId) {

        ObjectId objId;
        try {
            objId = new ObjectId(sessionId);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid sessionId format");
        }

        Optional<WorkoutSession> session = workoutSessionServices.findById(objId);

        if (session.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "WorkoutSession Not Found");
        }

        List<Workout> workoutList = session.get().getWorkouts();
        // Group workouts by exercise name
        Map<String, List<Workout>> mapOfWorkout = workoutList.stream()
                .collect(Collectors.groupingBy(Workout::getExerciseName));

        return mapOfWorkout;

    }

}
