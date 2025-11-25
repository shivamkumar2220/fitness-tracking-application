package com.sach.fitnesstracker.controller;

import com.sach.fitnesstracker.model.AppUser;
import com.sach.fitnesstracker.model.FitnessChallenge;
import com.sach.fitnesstracker.model.FitnessContent;
import com.sach.fitnesstracker.model.Workout;
import com.sach.fitnesstracker.service.AdminService;
import com.sach.fitnesstracker.service.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Set;
import java.util.stream.Collectors;

@Controller
public class UserController {

    private final UserService userService;
    private final AdminService adminService;
    private final Long defaultUserId;

    public UserController(UserService userService,
                          AdminService adminService,
                          @Value("${app.default-user-id:1}") Long defaultUserId) {
        this.userService = userService;
        this.adminService = adminService;
        this.defaultUserId = defaultUserId;
    }

    @GetMapping("/")
    public String landing() {
        return "redirect:/user?userId=" + defaultUserId;
    }

    @GetMapping("/user")
    public String userDashboard(@RequestParam(required = false) Long userId, Model model) {
        Long requestedId = (userId != null) ? userId : defaultUserId;
        AppUser currentUser = userService.resolveUserOrCreate(requestedId);
        Long actualUserId = currentUser.getId();
        if (!actualUserId.equals(requestedId)) {
            model.addAttribute("userNotice", "Requested user not found. Showing " + currentUser.getName() + " instead.");
        }
        model.addAttribute("user", currentUser);
        model.addAttribute("workouts", userService.workoutsForUser(actualUserId));
        model.addAttribute("workoutBreakdown", userService.workoutBreakdown(actualUserId));
        model.addAttribute("totalMinutes", userService.totalWorkoutMinutes(actualUserId));
        model.addAttribute("newWorkout", new Workout());
        model.addAttribute("challenges", adminService.challenges());
        model.addAttribute("joinedChallenges", currentUser.getChallenges());
        Set<Long> joinedIds = currentUser.getChallenges().stream()
                .map(FitnessChallenge::getId)
                .collect(Collectors.toSet());
        model.addAttribute("joinedChallengeIds", joinedIds);
        model.addAttribute("contentFeed", adminService.allContent().stream()
                .filter(FitnessContent::isApproved)
                .toList());
        return "user-dashboard";
    }

    @PostMapping("/user/{userId}/workouts")
    public String logWorkout(@PathVariable Long userId, @ModelAttribute Workout workout) {
        userService.logWorkout(userId, workout);
        return "redirect:/user?userId=" + userId;
    }

    @PostMapping("/user/{userId}/challenges/{challengeId}/join")
    public String joinChallenge(@PathVariable Long userId, @PathVariable Long challengeId) {
        userService.joinChallenge(userId, challengeId);
        return "redirect:/user?userId=" + userId;
    }

    @PostMapping("/user/{userId}/profile")
    public String updateProfile(@PathVariable Long userId, @ModelAttribute AppUser profile) {
        userService.updateProfile(userId, profile);
        return "redirect:/user?userId=" + userId;
    }
}

