package com.bzu.hotel_management_system.controller;

import com.bzu.hotel_management_system.DTO.RoomDTO;
import com.bzu.hotel_management_system.exception.BadRequestException;
import com.bzu.hotel_management_system.service.RoomService;
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
@RequestMapping("api/v1/rooms")
@Tag(name = "Room Management", description = "Operations related to room management")
public class RoomController {
    private final Logger log = LoggerFactory.getLogger(RoomController.class);

    private RoomService roomService;

    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }

    // get all rooms
    @Operation(
            summary = "Get all rooms",
            description = "Get all rooms in the system",
            responses = {
                    @ApiResponse(
                            description = "Successful operation",
                            responseCode = "200",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = RoomDTO.class)
                            )
                    ),
                    @ApiResponse(
                            description = "Bad request, invalid json",
                            responseCode = "400",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = RoomDTO.class)
                            )
                    ),
                    @ApiResponse(
                            description = "Unauthorized access",
                            responseCode = "401",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = RoomDTO.class)
                            )
                    ),
                    @ApiResponse(
                            description = "Forbidden",
                            responseCode = "403",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = RoomDTO.class)
                            )
                    ),
                    @ApiResponse(
                            description = "No rooms found",
                            responseCode = "404",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = RoomDTO.class)
                            )
                    )
            }
    )

    @GetMapping("/rooms")
    public ResponseEntity<List<RoomDTO>> getAllRooms() {
        log.info("Request to get all rooms");
        List<RoomDTO> rooms = roomService.getAllRooms();
        return new ResponseEntity<>(rooms, HttpStatus.OK);
    }

    // get room by id
    @Operation
            (
                    summary = "Get room by id",
                    description = "Get room by id",
                    responses = {
                            @ApiResponse(
                                    description = "Success",
                                    responseCode = "200"
                            ),
                            @ApiResponse(
                                    description = "Room not found",
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

    @GetMapping("/rooms/{id}")
    public ResponseEntity<RoomDTO> getRoomById(Long id) {
        log.info("Request to get room by id: {}", id);
        RoomDTO room = roomService.getRoomById(id);
        return new ResponseEntity<>(room, HttpStatus.OK);
    }

    // add room
    @Operation(
            summary = "Add a room",
            description = "Add a room to the system",
            responses = {
                    @ApiResponse(
                            description = "Room added successfully",
                            responseCode = "201"
                    ),
                    @ApiResponse(
                            description = "Invalid input",
                            responseCode = "400"
                    ),
                    @ApiResponse(
                            description = "Room already exists",
                            responseCode = "409"
                    ),
                    @ApiResponse(
                            description = "Internal server error",
                            responseCode = "500"
                    )
            }
    )

    @PostMapping
    public ResponseEntity<RoomDTO> addCustomer(@Valid @RequestBody RoomDTO roomDTO) {
        if (roomDTO.getRoomId() != null) {
            log.error("Cannot have an ID {}", roomDTO.getRoomId());
            throw new BadRequestException(RoomController.class.getSimpleName(), "id");
        }
        RoomDTO room = roomService.addRoom(roomDTO);
        return new ResponseEntity<>(room, HttpStatus.CREATED);
    }

    // update room
    @Operation(
            summary = "Update a room",
            description = "Update a room in the system",
            responses = {
                    @ApiResponse(
                            description = "Room updated successfully",
                            responseCode = "200"
                    ),
                    @ApiResponse(
                            description = "Invalid input",
                            responseCode = "400"
                    ),
                    @ApiResponse(
                            description = "Room not found",
                            responseCode = "404"
                    ),
                    @ApiResponse(
                            description = "Internal server error",
                            responseCode = "500"
                    )
            }
    )

    @PatchMapping("/rooms/{id}")
    public ResponseEntity<RoomDTO> updateRoom(@Valid @RequestBody RoomDTO roomDTO, @PathVariable Long id) {
        log.info("Request to update room by id: {}", id);
        RoomDTO room = roomService.updateRoom(roomDTO);
        return new ResponseEntity<>(room, HttpStatus.OK);
    }

    // delete room
    @Operation(
            summary = "Delete a room",
            description = "Delete a room in the system",
            responses = {
                    @ApiResponse(
                            description = "Room deleted successfully",
                            responseCode = "204"
                    ),
                    @ApiResponse(
                            description = "Room not found",
                            responseCode = "404"
                    ),
                    @ApiResponse(
                            description = "Internal server error",
                            responseCode = "500"
                    )
            }
    )

    @DeleteMapping("/rooms/{id}")
    public ResponseEntity<Void> deleteRoom(@PathVariable Long id) {
        log.info("Request to delete room by id: {}", id);
        roomService.deleteRoom(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
