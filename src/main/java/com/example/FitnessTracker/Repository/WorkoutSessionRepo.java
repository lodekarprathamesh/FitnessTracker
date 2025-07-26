package com.example.FitnessTracker.Repository;

import com.example.FitnessTracker.Entity.WorkoutSession;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

public interface WorkoutSessionRepo extends MongoRepository<WorkoutSession, ObjectId> {

}
