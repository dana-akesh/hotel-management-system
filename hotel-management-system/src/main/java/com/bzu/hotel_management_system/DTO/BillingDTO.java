package com.bzu.hotel_management_system.DTO;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BillingDTO {
    private Long id;
    private Long customerId;
    private Long reservationId;
    private double amount;
    private String date;
    private boolean isPaid;
}
