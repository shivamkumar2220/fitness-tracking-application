package com.sach.fitnesstracker.dto;

public record AdminDashboardStats(
        long adminCount,
        long userCount,
        long totalWorkouts,
        long approvedContent,
        long pendingContent,
        long challengeCount
) {
}

