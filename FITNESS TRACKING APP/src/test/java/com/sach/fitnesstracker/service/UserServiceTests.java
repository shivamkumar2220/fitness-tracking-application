package com.sach.fitnesstracker.service;

import com.sach.fitnesstracker.model.Workout;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class UserServiceTests {

    @Autowired
    private UserService userService;

    @Test
    @Transactional
    void logWorkoutCreatesBreakdownEntry() {
        Workout workout = new Workout();
        workout.setType("Cycling");
        workout.setDurationMinutes(20);
        workout.setIntensity("Medium");

        Workout saved = userService.logWorkout(2L, workout);

        assertThat(saved.getId()).isNotNull();
        Map<String, Long> breakdown = userService.workoutBreakdown(2L);
        assertThat(breakdown).containsKey("Cycling");
        assertThat(breakdown.get("Cycling")).isGreaterThanOrEqualTo(1L);
    }
}

