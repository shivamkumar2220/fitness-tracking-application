package com.sach.fitnesstracker.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Workout {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String type;
    private int durationMinutes;
    private String intensity;
    private LocalDateTime loggedAt = LocalDateTime.now();

    @ManyToOne(fetch = FetchType.LAZY)
    private AppUser user;

    public Workout() {
    }

    public Workout(AppUser user, String type, int durationMinutes, String intensity) {
        this.user = user;
        this.type = type;
        this.durationMinutes = durationMinutes;
        this.intensity = intensity;
    }

    public Long getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getDurationMinutes() {
        return durationMinutes;
    }

    public void setDurationMinutes(int durationMinutes) {
        this.durationMinutes = durationMinutes;
    }

    public String getIntensity() {
        return intensity;
    }

    public void setIntensity(String intensity) {
        this.intensity = intensity;
    }

    public LocalDateTime getLoggedAt() {
        return loggedAt;
    }

    public void setLoggedAt(LocalDateTime loggedAt) {
        this.loggedAt = loggedAt;
    }

    public AppUser getUser() {
        return user;
    }

    public void setUser(AppUser user) {
        this.user = user;
    }
}


