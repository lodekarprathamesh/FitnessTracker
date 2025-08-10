package com.example.FitnessTracker.Controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.example.FitnessTracker.Entity.User;
import com.example.FitnessTracker.Entity.WorkoutSession;
import com.example.FitnessTracker.Repository.RegisterRepository;
import com.example.FitnessTracker.Services.WorkoutSessionServices;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.time.LocalDate;
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
        User user = registerRepository.findByEmail(userName);

        List<ObjectId> allsessionsId = user.getSessionId();
        List<WorkoutSession> allsessions = new ArrayList<>();

        for (ObjectId sessionId : allsessionsId) {
            workoutSessionServices.findById(sessionId).ifPresent(allsessions::add);
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


    @PostMapping("/prevsession/delete")
    public ResponseEntity<Void> delete(@RequestParam("id") String id) {
        try {
            ObjectId sessionId = new ObjectId(id);
            workoutSessionServices.deleteById(sessionId);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }







}
