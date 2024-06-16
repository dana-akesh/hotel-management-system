package com.bzu.hotel_management_system.service;

import com.bzu.hotel_management_system.DTO.EmployeeDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface EmployeeService {

    List<EmployeeDTO> getAllEmployees();

    EmployeeDTO addEmployee(EmployeeDTO employeeDTO);

    EmployeeDTO updateEmployee(EmployeeDTO employeeDTO);

    EmployeeDTO getEmployeeById(Long id);

    void deleteEmployee(Long id);
}
