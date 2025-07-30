package com.example.FitnessTracker.Controller;

import com.example.FitnessTracker.Entity.User;
import com.example.FitnessTracker.Entity.Workout;
import com.example.FitnessTracker.Entity.WorkoutSession;
import com.example.FitnessTracker.Repository.RegisterRepository;
import com.example.FitnessTracker.Services.WorkoutSessionServices;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Controller
public class WorkoutSessionController {

    @Autowired
    private WorkoutSessionServices workoutSessionServices;
    @Autowired
    private RegisterRepository  registerRepository;

    @GetMapping("/session")
    public String sessionPage(@RequestParam(required = false) String sessionId, Model model) {

        //Getting that user
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        User user = registerRepository.findByEmail(userName);

        //checking if that id is present or not
        if(sessionId!=null && !sessionId.trim().isEmpty() && !user.getSessionId().contains(new ObjectId(sessionId))){
        //adding sessionId to user
            user.getSessionId().add(new  ObjectId(sessionId));
            registerRepository.save(user);
        }

        model.addAttribute("sessionId", sessionId);
        model.addAttribute("sessionStarted", sessionId != null && !sessionId.trim().isEmpty());
        model.addAttribute("workout", new Workout());

        List<Workout> workoutList = new ArrayList<>();

        if (sessionId != null && !sessionId.trim().isEmpty()) {
            Optional<WorkoutSession> session = workoutSessionServices.findById(new ObjectId(sessionId));
            LocalDateTime today = session.get().getStartTime();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");

            model.addAttribute("dateAndTime",today.format(formatter));
            if (session.isPresent()) {
                workoutList = session.get().getWorkouts();
            }
        }

        // Group workouts by exercise name
        Map<String, List<Workout>> groupedWorkouts = workoutList.stream()
                .collect(Collectors.groupingBy(Workout::getName));

        model.addAttribute("workoutsByExercise", groupedWorkouts);

        // Group workouts by sessionId
        Map<ObjectId, List<Workout>> allWorkoutsOfSession = new HashMap<>();
        if (sessionId != null && !sessionId.trim().isEmpty()) {
            allWorkoutsOfSession.put(new ObjectId(sessionId), workoutList);
        }
        model.addAttribute("allSessions", allWorkoutsOfSession);

        return "session";
    }



    @PostMapping("/session/start")
    public String startSession(RedirectAttributes redirectAttributes) {
        // Get logged-in username
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        //create new session
        WorkoutSession workoutSession = new WorkoutSession();
        workoutSession.setUserId(username);
        workoutSession.setStartTime(LocalDateTime.now());
        workoutSessionServices.saveWorkoutSession(workoutSession);

        redirectAttributes.addAttribute("sessionId",workoutSession.getId().toString());
        return "redirect:/session";

//        return "redirect:/session?sessionId=" + workoutSession.getId();
    }

    @PostMapping("/addWorkout")
    public String addWorkoutSession(@RequestParam("sessionId") ObjectId sessionId
                                , @ModelAttribute("workout") Workout workout,
                                    RedirectAttributes redirectAttributes) {
        Optional<WorkoutSession> optSession = workoutSessionServices.findById(sessionId);
        if(optSession.isEmpty()){
            redirectAttributes.addFlashAttribute("error", "No session found");
            return "redirect:/session";
        }

        WorkoutSession session  = optSession.get();
        session.getWorkouts().add(workout);

        workoutSessionServices.saveWorkoutSession(session);
        redirectAttributes.addFlashAttribute("success", "Workout session added");
        redirectAttributes.addAttribute("sessionId", sessionId.toString());
        return "redirect:/session";
//        return "redirect:/session?sessionId=" + sessionId;
    }

    @PostMapping("/endSession")
    public String endSession(@RequestParam("sessionId") ObjectId sessionId,RedirectAttributes redirectAttributes,Model model,@RequestParam("sessionName") String sessionName) {

        Optional<WorkoutSession> session = workoutSessionServices.findById(sessionId);
        if(session.isEmpty()){
            redirectAttributes.addFlashAttribute("error", "No session found");
        }
        session.get().setSessionName(sessionName);
        session.get().setEndTime(LocalDateTime.now());
        Duration duration =  Duration.between(session.get().getStartTime(), session.get().getEndTime());

        long hours = duration.toHours();
        long minutes = duration.toMinutes() % 60;
        long seconds = duration.getSeconds() % 60;

        String formattedDuration = hours + " H : " + minutes + " M : " + seconds + " S";
        session.get().setDuration(formattedDuration);
        workoutSessionServices.saveWorkoutSession(session.get());
        model.addAttribute("sessionName",sessionName);
        model.addAttribute("duration",formattedDuration);
        return "redirect:/prevsession";
    }

}
