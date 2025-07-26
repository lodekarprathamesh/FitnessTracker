package com.example.FitnessTracker.Controller;

import com.example.FitnessTracker.Entity.WorkoutSession;
import com.example.FitnessTracker.Services.WorkoutSessionServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import java.util.TreeMap;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
public class PrevSessionController {
    @Autowired
    private WorkoutSessionServices workoutSessionServices;

    @GetMapping("/prevsession")
    public String prevsession(Model model){

        List<WorkoutSession> allsessions = workoutSessionServices.getAllWorkoutSessions();

        Map<LocalDate,List<WorkoutSession>> groupedSessions = allsessions.stream().collect(Collectors.groupingBy(
                session -> session.getStartTime().toLocalDate(),
                TreeMap::new, // Sorted map by date
                Collectors.toList()
        ));
        model.addAttribute("allSessions",allsessions);
        model.addAttribute("groupedSessions", groupedSessions);
        return  "prevsession";
    }
}
