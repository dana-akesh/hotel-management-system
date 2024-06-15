package com.bzu.hotel_management_system.service;

import com.bzu.hotel_management_system.DTO.FacilityDTO;

public interface FacilityService {
    FacilityDTO addFacility(FacilityDTO facilityDTO);

    FacilityDTO updateFacility(FacilityDTO facilityDTO);

    FacilityDTO getFacilityById(Long id);

    void deleteFacility(Long id);
}
