package com.sach.fitnesstracker.controller;

import com.sach.fitnesstracker.dto.AdminDashboardStats;
import com.sach.fitnesstracker.model.AppUser;
import com.sach.fitnesstracker.model.FitnessChallenge;
import com.sach.fitnesstracker.model.FitnessContent;
import com.sach.fitnesstracker.model.Role;
import com.sach.fitnesstracker.model.SystemSetting;
import com.sach.fitnesstracker.service.AdminService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @GetMapping
    public String dashboard(Model model) {
        AdminDashboardStats stats = adminService.dashboardStats();
        model.addAttribute("users", adminService.allUsers());
        model.addAttribute("newUser", new AppUser());
        model.addAttribute("contentItems", adminService.allContent());
        model.addAttribute("newContent", new FitnessContent());
        model.addAttribute("settings", adminService.settings());
        model.addAttribute("newSetting", new SystemSetting());
        model.addAttribute("challenges", adminService.challenges());
        model.addAttribute("newChallenge", new FitnessChallenge());
        model.addAttribute("adminCount", stats.adminCount());
        model.addAttribute("userCount", stats.userCount());
        model.addAttribute("totalWorkouts", stats.totalWorkouts());
        model.addAttribute("stats", stats);
        return "admin-dashboard";
    }

    @PostMapping("/users")
    public String upsertUser(@ModelAttribute AppUser user) {
        adminService.saveUser(user);
        return "redirect:/admin";
    }

    @PostMapping("/users/{id}/delete")
    public String deleteUser(@PathVariable Long id) {
        adminService.deleteUser(id);
        return "redirect:/admin";
    }

    @PostMapping("/content")
    public String createContent(@ModelAttribute FitnessContent content) {
        adminService.saveContent(content);
        return "redirect:/admin";
    }

    @PostMapping("/content/{id}/approve")
    public String approveContent(@PathVariable Long id,
                                 @RequestParam(defaultValue = "true") boolean approved) {
        adminService.approveContent(id, approved);
        return "redirect:/admin";
    }

    @PostMapping("/settings")
    public String saveSetting(@ModelAttribute SystemSetting setting) {
        adminService.saveSetting(setting);
        return "redirect:/admin";
    }

    @PostMapping("/challenges")
    public String createChallenge(@RequestParam String name,
                                  @RequestParam String description,
                                  @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
                                  @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        adminService.createChallenge(name, description, startDate, endDate);
        return "redirect:/admin";
    }
}


