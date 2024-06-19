package com.bzu.hotel_management_system.service.imp;

import com.bzu.hotel_management_system.DTO.EmployeeDTO;
import com.bzu.hotel_management_system.entity.Employee;
import com.bzu.hotel_management_system.entity.Role;
import com.bzu.hotel_management_system.exception.ResourceNotFoundException;
import com.bzu.hotel_management_system.repository.EmployeeRepository;
import com.bzu.hotel_management_system.service.EmployeeService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeServiceImplementation implements EmployeeService {
    private EmployeeRepository employeeRepository;

    public EmployeeServiceImplementation(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public List<EmployeeDTO> getAllEmployees() {
        List<Employee> employees = employeeRepository.findAll();
        return employees.stream().map(this::mapToDTO).toList();
    }

    @Override
    public EmployeeDTO addEmployee(EmployeeDTO employeeDTO) {

        Employee employee = mapToEntity(employeeDTO);
        Employee newEmployee = employeeRepository.save(employee);

        EmployeeDTO employeeResponse = mapToDTO(newEmployee);
        return employeeResponse;
    }

    @Override
    public EmployeeDTO updateEmployee(EmployeeDTO employeeDTO) {
        // todo check if the employee exists
        Employee employee = employeeRepository.findById(employeeDTO.getEmployeeId()).orElseThrow(
                () -> new ResourceNotFoundException("Employee", "id", employeeDTO.getEmployeeId()));

        employee.setName(employeeDTO.getName());
        Employee updatedEmployee = employeeRepository.save(employee);

        return mapToDTO(updatedEmployee);
    }

    @Override
    public EmployeeDTO getEmployeeById(Long id) {
        Employee employee = employeeRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Employee", "id", id));

        return mapToDTO(employee);
    }

    @Override
    public void deleteEmployee(Long id) {
        Employee employee = employeeRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Employee", "id", id));

        employeeRepository.delete(employee);
    }

    // todo: implement this method
    private EmployeeDTO mapToDTO(Employee employee) {
        EmployeeDTO employeeDTO = new EmployeeDTO();

        employeeDTO.setEmployeeId(employee.getEmployeeId());
        employeeDTO.setName(employee.getName());
        employeeDTO.setPassword(employee.getPassword());
        employeeDTO.setRole(String.valueOf(employee.getRole()));
        employeeDTO.setUsername(employee.getUsername());
        employeeDTO.setTasks(employee.getTasks());

        return employeeDTO;
    }

    // todo: implement this method
    private Employee mapToEntity(EmployeeDTO employeeDTO) {
        Employee employee = new Employee();

        //todo employee.setEmployeeId(employeeDTO.getEmployeeId());
        employee.setName(employeeDTO.getName());
        employee.setTasks(employeeDTO.getTasks());
        employee.setPassword(employeeDTO.getPassword());
        employee.setRole(Role.valueOf(employeeDTO.getRole()));
        employee.setUsername(employeeDTO.getUsername());

        return employee;
    }
}
