package com.sach.fitnesstracker.service;

import com.sach.fitnesstracker.model.AppUser;
import com.sach.fitnesstracker.model.FitnessChallenge;
import com.sach.fitnesstracker.model.Role;
import com.sach.fitnesstracker.model.Workout;
import com.sach.fitnesstracker.repository.AppUserRepository;
import com.sach.fitnesstracker.repository.FitnessChallengeRepository;
import com.sach.fitnesstracker.repository.WorkoutRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final AppUserRepository userRepository;
    private final WorkoutRepository workoutRepository;
    private final FitnessChallengeRepository challengeRepository;

    public UserService(AppUserRepository userRepository,
                       WorkoutRepository workoutRepository,
                       FitnessChallengeRepository challengeRepository) {
        this.userRepository = userRepository;
        this.workoutRepository = workoutRepository;
        this.challengeRepository = challengeRepository;
    }

    public List<Workout> workoutsForUser(Long userId) {
        return workoutRepository.findByUserId(userId);
    }

    public Workout logWorkout(Long userId, Workout workout) {
        AppUser user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        workout.setUser(user);
        return workoutRepository.save(workout);
    }

    public List<FitnessChallenge> listChallenges() {
        return challengeRepository.findAll();
    }

    @Transactional
    public void joinChallenge(Long userId, Long challengeId) {
        AppUser user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        FitnessChallenge challenge = challengeRepository.findById(challengeId)
                .orElseThrow(() -> new IllegalArgumentException("Challenge not found"));
        if (!challenge.getParticipants().contains(user)) {
            challenge.getParticipants().add(user);
        }
    }

    public Optional<AppUser> getUser(Long userId) {
        return userRepository.findById(userId);
    }

    public AppUser updateProfile(Long userId, AppUser updated) {
        AppUser user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        user.setName(updated.getName());
        user.setEmail(updated.getEmail());
        user.setGoals(updated.getGoals());
        user.setPassword(updated.getPassword());
        return userRepository.save(user);
    }

    public Map<String, Long> workoutBreakdown(Long userId) {
        return workoutsForUser(userId).stream()
                .collect(Collectors.groupingBy(Workout::getType,
                        LinkedHashMap::new, Collectors.counting()));
    }

    public int totalWorkoutMinutes(Long userId) {
        return workoutsForUser(userId).stream()
                .mapToInt(Workout::getDurationMinutes)
                .sum();
    }

    public Optional<AppUser> firstAvailableUser() {
        var users = userRepository.findAll();
        return users.stream()
                .filter(u -> u.getRole() == Role.USER)
                .findFirst()
                .or(() -> users.stream().findFirst());
    }

    public AppUser ensureSampleUser() {
        AppUser fallback = new AppUser("Guest User", "guest@example.com", "guest123", Role.USER);
        fallback.setGoals("Add your first workout to get started!");
        return userRepository.save(fallback);
    }

    public AppUser resolveUserOrCreate(Long preferredId) {
        if (preferredId != null) {
            Optional<AppUser> userOpt = userRepository.findById(preferredId);
            if (userOpt.isPresent()) {
                return userOpt.get();
            }
        }
        return firstAvailableUser().orElseGet(this::ensureSampleUser);
    }
}


