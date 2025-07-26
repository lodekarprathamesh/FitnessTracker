package com.example.FitnessTracker.Entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Document(collection = "workoutSession")
@Data
@NoArgsConstructor //creates a no argument constructor
public class WorkoutSession {
    @Id
    private ObjectId id;
    private String userId;
    private String sessionName;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String duration;
    private List<Workout> workouts = new ArrayList<>();

}
