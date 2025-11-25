package com.sach.fitnesstracker.repository;

import com.sach.fitnesstracker.model.AppUser;
import com.sach.fitnesstracker.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AppUserRepository extends JpaRepository<AppUser, Long> {
    List<AppUser> findByRole(Role role);
}


