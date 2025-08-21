package com.example.FitnessTracker.Entity;

import lombok.Data;

import java.util.List;

@Data
public class Workout {
    private  String exerciseName;
    private List<Integer> sets;
    private  List<Integer> weight;
    private List<Integer> reps;
    private List<Integer> volume;
}
