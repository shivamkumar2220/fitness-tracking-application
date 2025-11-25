package com.sach.fitnesstracker.config;

import com.sach.fitnesstracker.model.*;
import com.sach.fitnesstracker.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class DataInitializer implements CommandLineRunner {

    private final AppUserRepository userRepository;
    private final WorkoutRepository workoutRepository;
    private final FitnessContentRepository contentRepository;
    private final SystemSettingRepository settingRepository;
    private final FitnessChallengeRepository challengeRepository;

    public DataInitializer(AppUserRepository userRepository,
                           WorkoutRepository workoutRepository,
                           FitnessContentRepository contentRepository,
                           SystemSettingRepository settingRepository,
                           FitnessChallengeRepository challengeRepository) {
        this.userRepository = userRepository;
        this.workoutRepository = workoutRepository;
        this.contentRepository = contentRepository;
        this.settingRepository = settingRepository;
        this.challengeRepository = challengeRepository;
    }

    @Override
    public void run(String... args) {
        if (userRepository.count() == 0) {
            AppUser admin = new AppUser("Admin User", "admin@example.com", "admin123", Role.ADMIN);
            AppUser jane = new AppUser("Jane Doe", "jane@example.com", "password", Role.USER);
            jane.setGoals("Run 5km three times a week");
            userRepository.save(admin);
            userRepository.save(jane);

            workoutRepository.save(new Workout(jane, "Running", 30, "High"));
            workoutRepository.save(new Workout(jane, "Yoga", 45, "Medium"));
        }

        if (contentRepository.count() == 0) {
            FitnessContent morningStretch = new FitnessContent("Morning Stretch Routine",
                    "Start your day with a 10-minute stretch.");
            morningStretch.setApproved(true);
            FitnessContent healthyEating = new FitnessContent("Healthy Eating Tips",
                    "Balance proteins, carbs, and fats.");
            healthyEating.setApproved(false);
            contentRepository.save(morningStretch);
            contentRepository.save(healthyEating);
        }

        if (settingRepository.count() == 0) {
            settingRepository.save(new SystemSetting("notifications.enabled", "true"));
            settingRepository.save(new SystemSetting("maintenance.mode", "false"));
        }

        if (challengeRepository.count() == 0) {
            challengeRepository.save(new FitnessChallenge(
                    "30-Day Cardio Blast",
                    "Complete 20 cardio sessions in 30 days.",
                    LocalDate.now(),
                    LocalDate.now().plusDays(30)
            ));

            challengeRepository.save(new FitnessChallenge(
                    "Core Strength Builder",
                    "Log 10 core workouts this month.",
                    LocalDate.now(),
                    LocalDate.now().plusDays(25)
            ));
        }
    }
}


