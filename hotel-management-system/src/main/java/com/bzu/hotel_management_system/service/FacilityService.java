package com.bzu.hotel_management_system.service;

import com.bzu.hotel_management_system.DTO.FacilityDTO;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public interface FacilityService {

    List<FacilityDTO> getAllFacilities();

    FacilityDTO addFacility(FacilityDTO facilityDTO);

    FacilityDTO updateFacility(FacilityDTO facilityDTO);

    FacilityDTO getFacilityById(Long id);

    void deleteFacility(Long id);
}
