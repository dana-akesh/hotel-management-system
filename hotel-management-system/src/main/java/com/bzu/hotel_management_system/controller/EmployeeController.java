package com.bzu.hotel_management_system.controller;

import com.bzu.hotel_management_system.DTO.EmployeeDTO;
import com.bzu.hotel_management_system.exception.BadRequestException;
import com.bzu.hotel_management_system.service.EmployeeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users/employees")
@Tag(name = "Employee Management", description = "Operations related to Employee management")
public class EmployeeController {
    private final Logger log = LoggerFactory.getLogger(EmployeeController.class);

    private EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    //get all employees
    @Operation(
            summary = "Get all facilities",
            description = "Get all rooms in the system",
            responses = {
                    @ApiResponse(
                            description = "Successful operation",
                            responseCode = "200",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = EmployeeDTO.class)
                            )
                    ),
                    @ApiResponse(
                            description = "Bad request, invalid json",
                            responseCode = "400",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = EmployeeDTO.class)
                            )
                    ),
                    @ApiResponse(
                            description = "Unauthorized access",
                            responseCode = "401",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = EmployeeDTO.class)
                            )
                    ),
                    @ApiResponse(
                            description = "Forbidden",
                            responseCode = "403",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = EmployeeDTO.class)
                            )
                    ),
                    @ApiResponse(
                            description = "No facilities found",
                            responseCode = "404",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = EmployeeDTO.class)
                            )
                    )
            }
    )
    @GetMapping
    public ResponseEntity<List<EmployeeDTO>> getAllFacilities() {
        log.info("Getting all employees");
        List<EmployeeDTO> employees = employeeService.getAllEmployees();
        return new ResponseEntity<>(employees, HttpStatus.OK);
    }

    //add employee
    @Operation(
            summary = "Add a employee",
            description = "Add a employee to the system",
            responses = {
                    @ApiResponse(
                            description = "employee added successfully",
                            responseCode = "201"
                    ),
                    @ApiResponse(
                            description = "Invalid input",
                            responseCode = "400"
                    ),
                    @ApiResponse(
                            description = "employee already exists",
                            responseCode = "409"
                    ),
                    @ApiResponse(
                            description = "Internal server error",
                            responseCode = "500"
                    )
            }
    )
    
    @PostMapping
    public ResponseEntity<EmployeeDTO> addEmployee(@Valid @RequestBody EmployeeDTO employeeDTO) {
        if (employeeDTO.getEmployeeId() != null) {
            log.error("Cannot have an ID {}", employeeDTO.getEmployeeId());
            throw new BadRequestException(EmployeeController.class.getSimpleName(), "id");
        }
        EmployeeDTO employee = employeeService.addEmployee(employeeDTO);
        return new ResponseEntity<>(employee, HttpStatus.CREATED);
    }

    //update employee
    @PatchMapping("/{id}")
    public ResponseEntity<EmployeeDTO> updateEmployee(@Valid @RequestBody EmployeeDTO employeeDTO, @PathVariable Long id) {
        log.info("Request to update employee by id: {}", id);
        EmployeeDTO employee  = employeeService.updateEmployee(employeeDTO);
        return new ResponseEntity<>(employee, HttpStatus.OK);
    }

    //get employee by id
    @Operation
            (
                    summary = "Get employee by id",
                    description = "Get employee by id",
                    responses = {
                            @ApiResponse(
                                    description = "Success",
                                    responseCode = "200"
                            ),
                            @ApiResponse(
                                    description = "employee not found",
                                    responseCode = "404"
                            ),
                            @ApiResponse(
                                    description = "Internal server error",
                                    responseCode = "500"
                            ),
                            @ApiResponse(
                                    description = "Bad request",
                                    responseCode = "400"
                            )
                    }
            )


    @GetMapping("/{id}")
    public ResponseEntity<EmployeeDTO> getEmployeeById(Long id) {
        log.info("Request to get employee by id: {}", id);
        EmployeeDTO employee = employeeService.getEmployeeById(id);
        return new ResponseEntity<>(employee, HttpStatus.OK);
    }
    
    //delete employee
    @Operation(
            summary = "Delete a employee",
            description = "Delete a employee in the system",
            responses = {
                    @ApiResponse(
                            description = "employee deleted successfully",
                            responseCode = "204"
                    ),
                    @ApiResponse(
                            description = "employee not found",
                            responseCode = "404"
                    ),
                    @ApiResponse(
                            description = "Internal server error",
                            responseCode = "500"
                    )
            }
    )


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRoom(@PathVariable Long id) {
        log.info("Request to delete employee by id: {}", id);
        employeeService.deleteEmployee(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}