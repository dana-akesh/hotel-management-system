package com.bzu.hotel_management_system.controller;

import com.bzu.hotel_management_system.DTO.BillingDTO;
import com.bzu.hotel_management_system.exception.BadRequestException;
import com.bzu.hotel_management_system.service.BillingService;
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
@RequestMapping("/api/v2/users/billings")
@Tag(name = "Billing", description = "Operations related to Billing management")
public class BillingController {
    private final Logger log = LoggerFactory.getLogger(BillingController.class);

    private BillingService billingService;

    public BillingController(BillingService billingService) {
        this.billingService = billingService;
    }

    //get all billings
    @Operation(
            summary = "Get all billings",
            description = "Get all billings in the system",
            responses = {
                    @ApiResponse(
                            description = "Successful operation",
                            responseCode = "200",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = BillingDTO.class)
                            )
                    ),
                    @ApiResponse(
                            description = "Bad request, invalid json",
                            responseCode = "400",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = BillingDTO.class)
                            )
                    ),
                    @ApiResponse(
                            description = "Unauthorized access",
                            responseCode = "401",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = BillingDTO.class)
                            )
                    ),
                    @ApiResponse(
                            description = "Forbidden",
                            responseCode = "403",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = BillingDTO.class)
                            )
                    ),
                    @ApiResponse(
                            description = "No billings found",
                            responseCode = "404",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = BillingDTO.class)
                            )
                    )
            }
    )
    @GetMapping
    public ResponseEntity<List<BillingDTO>> getAllBillings() {
        log.info("Getting all billings");
        List<BillingDTO> billings = billingService.getAllBillings();
        return new ResponseEntity<>(billings, HttpStatus.OK);
    }

    //add billing
    @Operation(
            summary = "Add a billing",
            description = "Add a billing to the system",
            responses = {
                    @ApiResponse(
                            description = "billing added successfully",
                            responseCode = "201"
                    ),
                    @ApiResponse(
                            description = "Invalid input",
                            responseCode = "400"
                    ),
                    @ApiResponse(
                            description = "billing already exists",
                            responseCode = "409"
                    ),
                    @ApiResponse(
                            description = "Internal server error",
                            responseCode = "500"
                    )
            }
    )

    @PostMapping
    public ResponseEntity<BillingDTO> addBilling(@Valid @RequestBody BillingDTO billingDTO) {
        if (billingDTO.getId() != null) {
            log.error("Cannot have an ID {}", billingDTO.getId());
            throw new BadRequestException(BillingController.class.getSimpleName(), "id");
        }
        BillingDTO billing = billingService.addBilling(billingDTO);
        return new ResponseEntity<>(billing, HttpStatus.CREATED);
    }

    //update billing
    @Operation(
            summary = "patch a billing",
            description = "patch billing information in the system",
            responses = {
                    @ApiResponse(
                            description = "billing updated successfully",
                            responseCode = "200"
                    ),
                    @ApiResponse(
                            description = "Invalid input",
                            responseCode = "400"
                    ),
                    @ApiResponse(
                            description = "billing not found",
                            responseCode = "404"
                    ),
                    @ApiResponse(
                            description = "Internal server error",
                            responseCode = "500"
                    )
            }
    )

    @PatchMapping("/{id}")
    public ResponseEntity<BillingDTO> updateBilling(@Valid @RequestBody BillingDTO billingDTO, @PathVariable Long id) {
        log.info("Request to update billing by id: {}", id);
        BillingDTO billing  = billingService.updateBilling(billingDTO);
        return new ResponseEntity<>(billing, HttpStatus.OK);
    }

    //get billing by id
    @Operation
            (
                    summary = "Get billing by id",
                    description = "Get billing by id",
                    responses = {
                            @ApiResponse(
                                    description = "Success",
                                    responseCode = "200"
                            ),
                            @ApiResponse(
                                    description = "billing not found",
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
    public ResponseEntity<BillingDTO> getBillingById(Long id) {
        log.info("Request to get billing by id: {}", id);
        BillingDTO billing = billingService.getBillingById(id);
        return new ResponseEntity<>(billing, HttpStatus.OK);
    }

    //delete billing
    @Operation(
            summary = "Delete a billing",
            description = "Delete a billing in the system",
            responses = {
                    @ApiResponse(
                            description = "billing deleted successfully",
                            responseCode = "204"
                    ),
                    @ApiResponse(
                            description = "billing not found",
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
        log.info("Request to delete billing by id: {}", id);
        billingService.deleteBilling(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
