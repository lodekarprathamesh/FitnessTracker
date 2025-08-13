package com.example.FitnessTracker.Repository;

import com.example.FitnessTracker.Entity.Exercises;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ExercisesRepository extends MongoRepository<Exercises, ObjectId> {

}
