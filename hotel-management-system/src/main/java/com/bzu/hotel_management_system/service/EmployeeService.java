package com.bzu.hotel_management_system.service;

import com.bzu.hotel_management_system.DTO.EmployeeDTO;

public interface EmployeeService {
    EmployeeDTO addEmployee(EmployeeDTO employeeDTO);

    EmployeeDTO updateEmployee(EmployeeDTO employeeDTO);

    EmployeeDTO getEmployeeById(Long id);

    void deleteEmployee(Long id);
}
