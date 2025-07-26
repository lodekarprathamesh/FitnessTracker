package com.example.FitnessTracker.Repository;

import com.example.FitnessTracker.Entity.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface RegisterRepository extends MongoRepository<User, ObjectId> {
    public User findByUsername(String username);
}
