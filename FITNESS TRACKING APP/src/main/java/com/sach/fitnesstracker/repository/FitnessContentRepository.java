package com.sach.fitnesstracker.repository;

import com.sach.fitnesstracker.model.FitnessContent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FitnessContentRepository extends JpaRepository<FitnessContent, Long> {
    List<FitnessContent> findByApprovedTrue();
}


