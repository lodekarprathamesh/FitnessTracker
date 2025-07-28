package com.example.FitnessTracker.Controller;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.example.FitnessTracker.Entity.User;
import com.example.FitnessTracker.Entity.Workout;
import com.example.FitnessTracker.Entity.WorkoutSession;
import com.example.FitnessTracker.Repository.RegisterRepository;
import com.example.FitnessTracker.Services.WorkoutSessionServices;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.stream.Collectors;

@Controller
public class PrevSessionController {
    @Autowired
    private WorkoutSessionServices workoutSessionServices;

    @Autowired
    private RegisterRepository registerRepository;

    @GetMapping("/prevsession")
    public String prevsession(Model model) throws JsonProcessingException {
        //getting user
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        User user = registerRepository.findByUsername(userName);

        List<ObjectId> allsessionsId = user.getSessionId();
        List<WorkoutSession> allsessions = new ArrayList<>();

        for (ObjectId sessionId : allsessionsId) {
            allsessions.add(workoutSessionServices.findById(sessionId).get());
        }

        Map<LocalDate,List<WorkoutSession>> groupedSessions = allsessions.stream().collect(Collectors.groupingBy(
                session -> session.getStartTime().toLocalDate(),
                TreeMap::new, // Sorted map by date
                Collectors.toList()
        ));

        for (WorkoutSession session : allsessions) {
            session.setIdString(session.getId().toHexString()); // Add this field manually
        }

        model.addAttribute("allSessions",allsessions);
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule()); // support LocalDateTime
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS); // format dates nicely

        //fetching session



        String allSessionsJson = mapper.writeValueAsString(allsessions);
        model.addAttribute("allSessionsJson", allSessionsJson);
        model.addAttribute("groupedSessions", groupedSessions);
        return  "prevsession";
    }

//    @GetMapping("/prevsession/{id}")
//    public List<Workout> workout(@PathVariable String id) {
//        Optional<WorkoutSession> wsession = workoutSessionServices.findById(new Object(id));
//        return wsession.get().getWorkouts();
//    }

}
