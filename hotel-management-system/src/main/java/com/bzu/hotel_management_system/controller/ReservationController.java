package com.bzu.hotel_management_system.controller;

import com.bzu.hotel_management_system.DTO.CustomerDTO;
import com.bzu.hotel_management_system.DTO.ReservationDTO;
import com.bzu.hotel_management_system.exception.BadRequestException;
import com.bzu.hotel_management_system.service.ReservationService;
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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/reservations")
@Tag(name = "Reservation", description = "Operations related to Reservation management")
public class ReservationController {
    private final Logger log = LoggerFactory.getLogger(ReservationController.class);

    private ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    // get all reservations
    @Operation(
            description = "Get reservation by ID",
            summary = "This is a summary for reservation GET endpoint",
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200"
                    ),
                    @ApiResponse(
                            description = "reservation not found",
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

    @GetMapping
    public ResponseEntity<List<ReservationDTO>> getAllReservations() {
        log.info("Getting all reservations");
        List<ReservationDTO> reservations = reservationService.getAllReservations();
        return new ResponseEntity<>(reservations, HttpStatus.OK);
    }

    //addReservation
    @Operation(
            description = "Add reservation",
            summary = "This is a summary for reservation POST endpoint",
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "201"
                    ),
                    @ApiResponse(
                            description = "reservation not found",
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

    @PostMapping
    public ResponseEntity<ReservationDTO> addReservation(@Valid @RequestBody ReservationDTO reservationDTO) {
        if (reservationDTO.getReservationId() != null) {
            log.error("Cannot have an ID {}", reservationDTO.getReservationId());
            throw new BadRequestException(ReservationController.class.getSimpleName(), "id");
        }
        ReservationDTO reservation = reservationService.addReservation(reservationDTO);
        return new ResponseEntity<>(reservation, HttpStatus.CREATED);
    }

    // updateReservation
    @Operation(
            description = "update reservation",
            summary = "This is a summary for reservation PATCH endpoint",
            responses = {
                    @ApiResponse(
                            description = "reservation updated successfully",
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
                            description = "reservation not found",
                            responseCode = "404"
                    ),
                    @ApiResponse(
                            description = "Internal server error",
                            responseCode = "500"
                    ),
                    @ApiResponse(
                            description = "no content",
                            responseCode = "204"
                    )
            }
    )

    @PatchMapping("/{id}")
    public ResponseEntity<ReservationDTO> updateReservation(@Valid @RequestBody ReservationDTO reservationDTO, @PathVariable Long id) {
        log.info("Request to update reservation by id: {}", id);
        ReservationDTO reservation = reservationService.updateReservation(reservationDTO);
        return new ResponseEntity<>(reservation, HttpStatus.OK);
    }

    // getReservationById
    @Operation(
            description = "Get reservation by ID",
            summary = "This is a summary for reservation GET endpoint",
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200"
                    ),
                    @ApiResponse(
                            description = "reservation not found",
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
    public ResponseEntity<ReservationDTO> getReservationById(@PathVariable Long id) {
        ReservationDTO reservation = reservationService.getReservationById(id);
        return new ResponseEntity<>(reservation, HttpStatus.OK);
    }

    // getReservationByUserId
    @Operation(
            description = "Get reservation by customer ID",
            summary = "This is a summary for reservation GET endpoint",
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200"
                    ),
                    @ApiResponse(
                            description = "reservation not found",
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
    @GetMapping("/customers/{id}")
    public ResponseEntity<ReservationDTO> getReservationByUserId(@PathVariable Long id) {
        log.info("Request to get reservation by customer id: {}", id);
        ReservationDTO reservation = reservationService.getReservationByUserId(id);
        return new ResponseEntity<>(reservation, HttpStatus.OK);
    }

    // getReservationByUserName
    @Operation(
            description = "Get reservation by customer name",
            summary = "This is a summary for reservation GET endpoint",
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200"
                    ),
                    @ApiResponse(
                            description = "reservation not found",
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
    @GetMapping("/customers/{name}")
    public ResponseEntity<ReservationDTO> getReservationByUserName(@PathVariable String name) {
        log.info("Request to get reservation by customer name: {}", name);
        ReservationDTO reservation = reservationService.getReservationByUserName(name);
        return new ResponseEntity<>(reservation, HttpStatus.OK);
    }

    // getReservationByDate
    @Operation(
            description = "Get reservation by date",
            summary = "This is a summary for reservation GET endpoint",
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200"
                    ),
                    @ApiResponse(
                            description = "reservation not found",
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
    @GetMapping("/reservations/{date}")
    public ResponseEntity<ReservationDTO> getReservationByDate(@PathVariable String date) {
        log.info("Request to get reservation by date: {}", date);
        ReservationDTO reservation = reservationService.getReservationByDate(date);
        return new ResponseEntity<>(reservation, HttpStatus.OK);
    }

    // approveCheckIn
    @Operation(
            description = "approve check in",
            summary = "This is a summary for reservation PATCH endpoint",
            responses = {
                    @ApiResponse(
                            description = "reservation checked in successfully",
                            responseCode = "200"
                    ),
                    @ApiResponse(
                            description = "reservation not found",
                            responseCode = "404"
                    ),
                    @ApiResponse(
                            description = "Internal server error",
                            responseCode = "500"
                    ),
                    @ApiResponse(
                            description = "no content",
                            responseCode = "204"
                    )
            }
    )

    @PatchMapping("/checkin/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> approveCheckIn(@PathVariable Long id) {
        log.info("Request to approve check in by id: {}", id);
        reservationService.approveCheckIn(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // approveCheckOut
    @Operation(
            description = "approve check out",
            summary = "This is a summary for reservation PATCH endpoint",
            responses = {
                    @ApiResponse(
                            description = "reservation checked out successfully",
                            responseCode = "200"
                    ),
                    @ApiResponse(
                            description = "reservation not found",
                            responseCode = "404"
                    ),
                    @ApiResponse(
                            description = "Internal server error",
                            responseCode = "500"
                    ),
                    @ApiResponse(
                            description = "no content",
                            responseCode = "204"
                    )
            }
    )
    @PatchMapping("/checkout/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> approveCheckOut(@PathVariable Long id) {
        log.info("Request to approve check out by id: {}", id);
        reservationService.approveCheckOut(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    // approveCancelReservation
    @Operation(
            description = "approve cancel reservation",
            summary = "This is a summary for reservation PATCH endpoint",
            responses = {
                    @ApiResponse(
                            description = "reservation cancelled successfully",
                            responseCode = "200"
                    ),
                    @ApiResponse(
                            description = "reservation not found",
                            responseCode = "404"
                    ),
                    @ApiResponse(
                            description = "Internal server error",
                            responseCode = "500"
                    )
            }
    )
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> approveCancelReservation(@PathVariable Long id) {
        log.info("Request to approve cancel reservation by id: {}", id);
        reservationService.approveCancelReservation(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
