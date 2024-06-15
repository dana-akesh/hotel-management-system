package com.bzu.hotel_management_system.repository;

import com.bzu.hotel_management_system.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
}
