package com.sach.fitnesstracker.service;

import com.sach.fitnesstracker.dto.AdminDashboardStats;
import com.sach.fitnesstracker.model.AppUser;
import com.sach.fitnesstracker.model.FitnessChallenge;
import com.sach.fitnesstracker.model.FitnessContent;
import com.sach.fitnesstracker.model.Role;
import com.sach.fitnesstracker.model.SystemSetting;
import com.sach.fitnesstracker.repository.AppUserRepository;
import com.sach.fitnesstracker.repository.FitnessChallengeRepository;
import com.sach.fitnesstracker.repository.FitnessContentRepository;
import com.sach.fitnesstracker.repository.SystemSettingRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class AdminService {

    private final AppUserRepository userRepository;
    private final FitnessContentRepository contentRepository;
    private final SystemSettingRepository settingRepository;
    private final FitnessChallengeRepository challengeRepository;

    public AdminService(AppUserRepository userRepository,
                        FitnessContentRepository contentRepository,
                        SystemSettingRepository settingRepository,
                        FitnessChallengeRepository challengeRepository) {
        this.userRepository = userRepository;
        this.contentRepository = contentRepository;
        this.settingRepository = settingRepository;
        this.challengeRepository = challengeRepository;
    }

    public List<AppUser> allUsers() {
        return userRepository.findAll();
    }

    public AppUser saveUser(AppUser user) {
        return userRepository.save(user);
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    public List<FitnessContent> allContent() {
        return contentRepository.findAll();
    }

    public FitnessContent saveContent(FitnessContent content) {
        return contentRepository.save(content);
    }

    public void approveContent(Long id, boolean approved) {
        contentRepository.findById(id).ifPresent(content -> {
            content.setApproved(approved);
            contentRepository.save(content);
        });
    }

    public List<SystemSetting> settings() {
        return settingRepository.findAll();
    }

    public SystemSetting saveSetting(SystemSetting setting) {
        return settingRepository.save(setting);
    }

    public List<FitnessChallenge> challenges() {
        return challengeRepository.findAll();
    }

    public FitnessChallenge createChallenge(String name, String description,
                                            LocalDate start, LocalDate end) {
        FitnessChallenge challenge = new FitnessChallenge(name, description, start, end);
        return challengeRepository.save(challenge);
    }

    public long countUsersByRole(Role role) {
        return userRepository.findByRole(role).size();
    }

    public long totalWorkoutsLogged() {
        return userRepository.findAll().stream()
                .mapToLong(user -> user.getWorkouts().size())
                .sum();
    }

    public AdminDashboardStats dashboardStats() {
        long adminCount = countUsersByRole(Role.ADMIN);
        long userCount = countUsersByRole(Role.USER);
        long totalWorkouts = totalWorkoutsLogged();
        long approvedContent = contentRepository.findByApprovedTrue().size();
        long totalContent = contentRepository.count();
        long pendingContent = totalContent - approvedContent;
        long challengeCount = challengeRepository.count();
        return new AdminDashboardStats(adminCount, userCount, totalWorkouts,
                approvedContent, pendingContent, challengeCount);
    }
}


