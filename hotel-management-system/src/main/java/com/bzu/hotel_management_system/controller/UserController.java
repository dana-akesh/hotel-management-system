package com.bzu.hotel_management_system.controller;

import com.bzu.hotel_management_system.DTO.UserDTO;
import com.bzu.hotel_management_system.entity.User;
import com.bzu.hotel_management_system.exception.BadRequestException;
import com.bzu.hotel_management_system.service.UserService;
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
@RequestMapping("/api/v1/users")
@Tag(name = "User Management", description = "Operations related to user management")
public class UserController {
    private final Logger log = LoggerFactory.getLogger(UserController.class);

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // Get user by ID
    @Operation(
            description = "Get user by ID",
            summary = "This is a summary for users GET endpoint",
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200"
                    ),
                    @ApiResponse(
                            description = "User not found",
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

    @GetMapping("/users/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable(name = "id") Long id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }


    // add new user
    @Operation(
            description = "Add new user",
            summary = "This is a summary for users POST endpoint",
            responses = {
                    @ApiResponse(
                            description = "User created successfully",
                            responseCode = "201",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = UserDTO.class)
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
    public ResponseEntity<UserDTO> addUser(@Valid @RequestBody UserDTO userDTO) {
        if (userDTO.getUserId() != null) {
            log.error("Cannot have an ID {}", userDTO);
            throw new BadRequestException(UserController.class.getSimpleName(), "id");
        }
        UserDTO user = userService.addUser(userDTO);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    // update user
    @Operation(
            summary = "Update User",
            description = "Update user details using PATCH method",
            responses = {
                    @ApiResponse(
                            description = "User updated successfully",
                            responseCode = "200",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = UserDTO.class)
                            )
                    ),
                    @ApiResponse(
                            description = "Invalid input",
                            responseCode = "400"
                    ),
                    @ApiResponse(
                            description = "User not found",
                            responseCode = "404"
                    ),
                    @ApiResponse(
                            description = "Internal server error",
                            responseCode = "500"
                    )
            }
    )

    @PatchMapping("/users/{id}")
    public ResponseEntity<UserDTO> updateUser(@RequestBody UserDTO userDTO, @PathVariable(name = "id") Long id) {
        // Logic to update the user
        if (userDTO == null || userDTO.getName() == null || userDTO.getUsername() == null ) {
            throw new BadRequestException("Invalid user data", "user data");
        }

        // check if user exists
        if (userService.getUserById(id) == null ) {
            throw new BadRequestException("User not found", "user not found");
        }

        UserDTO updatedUser = userService.updateUser(userDTO, id);
        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }

    // delete user
    @Operation(
            summary = "Delete User",
            description = "Delete user by ID",
            responses = {
                    @ApiResponse(
                            responseCode = "204",
                            description = "User deleted successfully"
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "User not found"
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Internal server error"
                    )
            }
    )

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable(name = "id") Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build(); // return 204
    }
}
