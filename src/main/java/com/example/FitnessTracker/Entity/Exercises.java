package com.example.FitnessTracker.Entity;

import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "exercises")
@Data
public class Exercises {
    @Id
    private ObjectId id;

    private String name;
}
