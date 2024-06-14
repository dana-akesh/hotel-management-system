package com.bzu.hotel_management_system.DTO;

import edu.bzu.hotelManagementSystem.entity.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReservationDTO {
    private long reservationId;
    private String date;
    private String status;
}
