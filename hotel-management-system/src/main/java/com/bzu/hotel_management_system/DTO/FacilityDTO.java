package com.bzu.hotel_management_system.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FacilityDTO {

    private Long facilityId;
    private String facilityName;
    private String facilityDescription;

}
