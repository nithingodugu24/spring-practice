package com.nithingodugu.hospitalManagement.repository;

import com.nithingodugu.hospitalManagement.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentRepository extends JpaRepository<Department, Long> {
}