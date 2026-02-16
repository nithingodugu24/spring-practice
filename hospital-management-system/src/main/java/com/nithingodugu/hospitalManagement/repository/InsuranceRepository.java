package com.nithingodugu.hospitalManagement.repository;

import com.nithingodugu.hospitalManagement.entity.Insurance;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InsuranceRepository extends JpaRepository<Insurance, Long> {
}