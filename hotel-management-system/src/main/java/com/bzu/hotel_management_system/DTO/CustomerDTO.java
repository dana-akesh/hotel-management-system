package com.bzu.hotel_management_system.DTO;

import com.bzu.hotel_management_system.entity.Reservation;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDTO extends UserDTO {

    private Long customerId;
    private List<Reservation> reservations;
}
