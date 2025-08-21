package com.example.FitnessTracker.Entity;

import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;

import java.util.List;
import java.util.Map;

@Data
public class SaveSessionRequest {
    @Id
    private ObjectId id;

    private String sessionName;
    private Map<String, List<Workout>> workouts;
}
