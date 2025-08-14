package com.example.FitnessTracker.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Document(collection = "workoutSession")
@Data
@NoArgsConstructor
@AllArgsConstructor//creates a no argument constructor
public class WorkoutSession {
    @Id
    private ObjectId id;

    @Transient
    private String idString; // Only used for JSON transfer, not persisted

    public void setIdString(String id) {
        this.idString = id;
    }
    public String getIdString() {
        return this.idString;
    }


    private String userId;
    private String sessionName;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String duration;
    private List<Workout> workouts = new ArrayList<>();

}
