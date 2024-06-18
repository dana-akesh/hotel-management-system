package com.bzu.hotel_management_system.controller;

import com.bzu.hotel_management_system.DTO.FacilityDTO;
import com.bzu.hotel_management_system.exception.BadRequestException;
import com.bzu.hotel_management_system.service.FacilityService;
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
@RequestMapping("/api/v1/facilities")
@Tag(name = "Facility Management", description = "Operations related to facility management")
public class FacilityController {
    private final Logger log = LoggerFactory.getLogger(FacilityController.class);

    private FacilityService facilityService;

    public FacilityController(FacilityService facilityService) {
        this.facilityService = facilityService;
    }

    // get all facilities
    @Operation(
            summary = "Get all facilities",
            description = "Get all rooms in the system",
            responses = {
                    @ApiResponse(
                            description = "Successful operation",
                            responseCode = "200",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = FacilityDTO.class)
                            )
                    ),
                    @ApiResponse(
                            description = "Bad request, invalid json",
                            responseCode = "400",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = FacilityDTO.class)
                            )
                    ),
                    @ApiResponse(
                            description = "Unauthorized access",
                            responseCode = "401",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = FacilityDTO.class)
                            )
                    ),
                    @ApiResponse(
                            description = "Forbidden",
                            responseCode = "403",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = FacilityDTO.class)
                            )
                    ),
                    @ApiResponse(
                            description = "No facilities found",
                            responseCode = "404",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = FacilityDTO.class)
                            )
                    )
            }
    )

    @GetMapping
    public ResponseEntity<List<FacilityDTO>> getAllFacilities() {
        log.info("Getting all facilities");
        List<FacilityDTO> facilities = facilityService.getAllFacilities();
        return new ResponseEntity<>(facilities, HttpStatus.OK);
    }

    // Add facility
    @Operation(
            summary = "Add a facility",
            description = "Add a facility to the system",
            responses = {
                    @ApiResponse(
                            description = "facility added successfully",
                            responseCode = "201"
                    ),
                    @ApiResponse(
                            description = "Invalid input",
                            responseCode = "400"
                    ),
                    @ApiResponse(
                            description = "facility already exists",
                            responseCode = "409"
                    ),
                    @ApiResponse(
                            description = "Internal server error",
                            responseCode = "500"
                    )
            }
    )


    @PostMapping
    public ResponseEntity<FacilityDTO> addFacility(@Valid @RequestBody FacilityDTO facilityDTO) {
        if (facilityDTO.getFacilityId() != null) {
            log.error("Cannot have an ID {}", facilityDTO.getFacilityId());
            throw new BadRequestException(FacilityController.class.getSimpleName(), "id");
        }
        FacilityDTO facility = facilityService.addFacility(facilityDTO);
        return new ResponseEntity<>(facility, HttpStatus.CREATED);
    }

    // Update facility
    @Operation(
            summary = "Update a facility",
            description = "Update a facility in the system",
            responses = {
                    @ApiResponse(
                            description = "facility updated successfully",
                            responseCode = "200"
                    ),
                    @ApiResponse(
                            description = "Invalid input",
                            responseCode = "400"
                    ),
                    @ApiResponse(
                            description = "facility not found",
                            responseCode = "404"
                    ),
                    @ApiResponse(
                            description = "Internal server error",
                            responseCode = "500"
                    )
            }
    )

    @PatchMapping("/{id}")
    public ResponseEntity<FacilityDTO> updateFacility(@Valid @RequestBody FacilityDTO facilityDTO, @PathVariable Long id) {
        log.info("Request to update facility by id: {}", id);
        FacilityDTO facility  = facilityService.updateFacility(facilityDTO);
        return new ResponseEntity<>(facility, HttpStatus.OK);
    }


    // Get facility by id
    @Operation
            (
                    summary = "Get facility by id",
                    description = "Get facility by id",
                    responses = {
                            @ApiResponse(
                                    description = "Success",
                                    responseCode = "200"
                            ),
                            @ApiResponse(
                                    description = "facility not found",
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
    public ResponseEntity<FacilityDTO> getFacilityById(Long id) {
        log.info("Request to get facility by id: {}", id);
        FacilityDTO facility = facilityService.getFacilityById(id);
        return new ResponseEntity<>(facility, HttpStatus.OK);
    }

    // Delete facility
    @Operation(
            summary = "Delete a facility",
            description = "Delete a facility in the system",
            responses = {
                    @ApiResponse(
                            description = "facility deleted successfully",
                            responseCode = "204"
                    ),
                    @ApiResponse(
                            description = "facility not found",
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
        log.info("Request to delete facility by id: {}", id);
        facilityService.deleteFacility(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
