package com.bzu.hotel_management_system.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoomDTO {
    private Long roomId;
    private String type;
    private int price;
    private String status;
    private String details;
    private int capacity;
    private String size;
    private String features;
    private Long facilityId;

}
