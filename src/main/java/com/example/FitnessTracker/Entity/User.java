package com.example.FitnessTracker.Entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "User")
@NoArgsConstructor
@Data
public class User {
    @Id
    private ObjectId id;

    @Indexed(unique = true)//for unique username
    private String username;
    private String password;
    private ArrayList<ObjectId> sessionId =  new ArrayList<>();
    private List<String> Roles;
}
