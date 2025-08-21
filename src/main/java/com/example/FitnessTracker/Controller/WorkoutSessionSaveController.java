package com.example.FitnessTracker.Controller;

import com.example.FitnessTracker.Entity.SaveSessionRequest;
import com.example.FitnessTracker.Entity.User;
import com.example.FitnessTracker.Entity.Workout;
import com.example.FitnessTracker.Entity.WorkoutSession;
import com.example.FitnessTracker.Repository.RegisterRepository;
import com.example.FitnessTracker.Services.WorkoutSessionServices;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/sessionSave")
public class WorkoutSessionSaveController {

    @Autowired
    private RegisterRepository registerRepository;

    @Autowired
    private WorkoutSessionServices workoutSessionServices;

    @PostMapping("/saveTable")
    public ResponseEntity<?> saveSession(
            @RequestParam("sessionId") String sessionId,
            @RequestBody SaveSessionRequest request) {

        //request is the DTO whichhas sessonName and Map of data
        String sessionName = request.getSessionName();
        Map<String, List<Workout>> data = request.getWorkouts();



        // 1. Get logged in user
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        User user = registerRepository.findByEmail(userName);
        ObjectId objectId = new ObjectId(sessionId);

        // 2. Attach sessionId to user
        if (objectId != null) {
            user.getSessionId().add(objectId);
            registerRepository.save(user);
        }

        // 3. Get the session once
        Optional<WorkoutSession> optSession = workoutSessionServices.findById(objectId);
        if (optSession.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", "No session found"));
        }

        WorkoutSession session = optSession.get();

        //put sessionName
        session.setSessionName(sessionName);
        session.setEndTime(LocalDateTime.now());

        // duration between start and end
        Duration duration = Duration.between(session.getStartTime(), session.getEndTime());


        // Or as a formatted string (e.g. HH:mm:ss):
        long hours = duration.toHours();
        long minutes = duration.toMinutesPart();
        long seconds = duration.toSecondsPart();
        session.setDuration(String.format("%02d:%02d:%02d", hours, minutes, seconds));

        List<Workout> workouts = session.getWorkouts();

        // 4. Merge workouts from request into session
        for (Map.Entry<String, List<Workout>> entry : data.entrySet()) {
            String exerciseName = entry.getKey();
            List<Workout> newWorkouts = entry.getValue();

            for (Workout workout : newWorkouts) {
                System.out.println("Saving workout: " + workout);
                workouts.add(workout); // ✅ actually store the workout
            }
        }

        //Save session
        workoutSessionServices.saveWorkoutSession(session);

        // 5. Return JSON response (for fetch in JS) and send to redirect
        return ResponseEntity.ok(Map.of("message", "Session saved successfully",
                "redirectUrl", "/prevsession"));
    }

    @GetMapping("/showSession/{sessionId}")
    public WorkoutSession showSession(@PathVariable String sessionId , Model model) throws JsonProcessingException {
        ObjectId session = new ObjectId(sessionId);
        Optional<WorkoutSession> ws = workoutSessionServices.findById(session);

        return ws.orElse(null);
    }

}
