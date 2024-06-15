package com.bzu.hotel_management_system.repository;

import com.bzu.hotel_management_system.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {
    @Query("SELECT t FROM Task t WHERE t.employee.employeeId = :employeeId")
    List<Task> findByEmployeeId(@Param("employeeId") Long employeeId);
}
