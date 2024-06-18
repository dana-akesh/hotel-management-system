package com.bzu.hotel_management_system.controller;

import com.bzu.hotel_management_system.DTO.CustomerDTO;
import com.bzu.hotel_management_system.exception.BadRequestException;
import com.bzu.hotel_management_system.service.CustomerService;
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

@RestController
@RequestMapping("/api/v1/users/customers")
@Tag(name = "Customer Management", description = "Operations related to customer management")
public class CustomerController {
    private final Logger log = LoggerFactory.getLogger(CustomerController.class);

    private CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    // Get customer by ID
    @Operation(
            description = "Get customer by ID",
            summary = "This is a summary for customers GET endpoint",
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200"
                    ),
                    @ApiResponse(
                            description = "Customer not found",
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
    public ResponseEntity<CustomerDTO> getCustomerById(@PathVariable(name = "id") Long id) {
        return ResponseEntity.ok(customerService.getCustomerById(id));
    }

    // add customer
    @Operation(
            description = "add customer",
            summary = "This is a summary for customers POST endpoint",
            responses = {
                    @ApiResponse(
                            description = "User created successfully",
                            responseCode = "201",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = CustomerDTO.class)
                            )
                    ),
                    @ApiResponse(
                            description = "Invalid input",
                            responseCode = "400"
                    ),
                    @ApiResponse(
                            description = "User already exists",
                            responseCode = "409"
                    ),
                    @ApiResponse(
                            description = "Internal server error",
                            responseCode = "500"
                    )
            }

    )

    @PostMapping
    public ResponseEntity<CustomerDTO> addCustomer(@Valid @RequestBody CustomerDTO customerDTO) {
        if (customerDTO.getUserId() != null) {
            log.error("Cannot have an ID {}", customerDTO.getUserId());
            throw new BadRequestException(CustomerController.class.getSimpleName(), "id");
        }
        CustomerDTO user = customerService.addCustomer(customerDTO);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    // update customer
    @Operation(
            description = "update customer",
            summary = "This is a summary for customers PATCH endpoint",
            responses = {
                    @ApiResponse(
                            description = "customer updated successfully",
                            responseCode = "200",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = CustomerDTO.class)
                            )
                    ),
                    @ApiResponse(
                            description = "Invalid input",
                            responseCode = "400"
                    ),
                    @ApiResponse(
                            description = "customer not found",
                            responseCode = "404"
                    ),
                    @ApiResponse(
                            description = "Internal server error",
                            responseCode = "500"
                    )
            }
    )

    @PatchMapping("/{id}")
    public ResponseEntity<CustomerDTO> updateCustomerById(@PathVariable(name = "id") Long id, @Valid @RequestBody CustomerDTO customerDTO) {
        // logic to update customer
        if (customerDTO == null || customerDTO.getName() == null || customerDTO.getUsername() == null) {
            throw new BadRequestException("Invalid user data", "user data");
        }

        // check if user exists
        if (customerService.getCustomerById(id) == null) {
            throw new BadRequestException("customer not found", "customer not found");
        }

        CustomerDTO user = customerService.updateCustomerById(customerDTO, id);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    // delete customer
    @Operation(
            description = "delete customer",
            summary = "This is a summary for customers DELETE endpoint",
            responses = {
                    @ApiResponse(
                            description = "customer deleted successfully",
                            responseCode = "200"
                    ),
                    @ApiResponse(
                            description = "customer not found",
                            responseCode = "404"
                    ),
                    @ApiResponse(
                            description = "Internal server error",
                            responseCode = "500"
                    )
            }
    )

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable(name = "id") Long id) {
        customerService.deleteCustomer(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    // change password
    @Operation(
            description = "change password",
            summary = "This is a summary for customers PATCH endpoint",
            responses = {
                    @ApiResponse(
                            description = "password changed successfully",
                            responseCode = "200",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = CustomerDTO.class)
                            )
                    ),
                    @ApiResponse(
                            description = "Invalid input",
                            responseCode = "400"
                    ),
                    @ApiResponse(
                            description = "customer not found",
                            responseCode = "404"
                    ),
                    @ApiResponse(
                            description = "Internal server error",
                            responseCode = "500"
                    )
            }
    )

    @PatchMapping("/{id}/change-password")
    public ResponseEntity<CustomerDTO> changePassword(@PathVariable(name = "id") Long id, @Valid @RequestBody CustomerDTO customerDTO) {
        // logic to change password
        if (customerDTO == null || customerDTO.getPassword() == null) {
            throw new BadRequestException("Invalid password", "password");
        }

        // check if user exists
        if (customerService.getCustomerById(id) == null) {
            throw new BadRequestException("customer not found", "customer not found");
        }

        CustomerDTO user = customerService.changePassword(customerDTO.getPassword(), id);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    // get customer reservations
    @Operation(
            description = "get customer reservations",
            summary = "This is a summary for customers GET endpoint",
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200"
                    ),
                    @ApiResponse(
                            description = "Customer not found",
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

    @GetMapping("/{id}/reservations")
    public ResponseEntity<CustomerDTO> getCustomerReservations(@PathVariable(name = "id") Long id) {
        return ResponseEntity.ok(customerService.getCustomerReservations(id));
    }

}
