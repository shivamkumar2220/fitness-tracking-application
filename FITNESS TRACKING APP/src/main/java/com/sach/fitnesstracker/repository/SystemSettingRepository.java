package com.sach.fitnesstracker.repository;

import com.sach.fitnesstracker.model.SystemSetting;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SystemSettingRepository extends JpaRepository<SystemSetting, String> {
}


