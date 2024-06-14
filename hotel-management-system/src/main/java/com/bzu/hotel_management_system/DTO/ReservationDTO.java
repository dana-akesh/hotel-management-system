package com.bzu.hotel_management_system.DTO;


import com.bzu.hotel_management_system.entity.Customer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReservationDTO {
    private long reservationId;
    private Customer customer;
    private String date;
    private String status;
}
