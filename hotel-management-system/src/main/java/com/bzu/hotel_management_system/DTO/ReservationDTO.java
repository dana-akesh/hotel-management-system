package com.bzu.hotel_management_system.DTO;


import com.bzu.hotel_management_system.entity.Customer;
import com.bzu.hotel_management_system.entity.ReservationRoom;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReservationDTO {
    private Long reservationId;
    private Customer customer;
    private Boolean isCheckIn;
    private Boolean isCheckOut;
    private String date;
    private String status;
    private ReservationRoom reservationRoom;
}
