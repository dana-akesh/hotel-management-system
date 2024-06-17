package com.bzu.hotel_management_system.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReservationRoomDTO {
    private Long reservationRoomId;
    private Long reservationId;
    private Long roomId;
}
