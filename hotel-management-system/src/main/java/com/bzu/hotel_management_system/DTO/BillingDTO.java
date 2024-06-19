package com.bzu.hotel_management_system.DTO;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BillingDTO {
    private Long id;
    private double amount;
    private String date;
    private boolean isPaid;
    private List<CustomerDTO> customers;
    private List<ReservationDTO> reservations;
}
