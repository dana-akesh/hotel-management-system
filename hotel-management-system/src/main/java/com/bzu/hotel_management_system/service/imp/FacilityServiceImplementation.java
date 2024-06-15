package com.bzu.hotel_management_system.service.imp;

import com.bzu.hotel_management_system.DTO.FacilityDTO;
import com.bzu.hotel_management_system.entity.Facility;
import com.bzu.hotel_management_system.exception.ResourceNotFoundException;
import com.bzu.hotel_management_system.repository.FacilityRepository;
import com.bzu.hotel_management_system.service.FacilityService;


public class FacilityServiceImplementation implements FacilityService {
    private FacilityRepository facilityRepository;

    public FacilityServiceImplementation(FacilityRepository facilityRepository) {
        this.facilityRepository = facilityRepository;
    }

    @Override
    public FacilityDTO addFacility(FacilityDTO facilityDTO) {

        Facility facility = mapToEntity(facilityDTO);
        Facility newFacility = facilityRepository.save(facility);

        FacilityDTO facilityResponse = mapToDTO(newFacility);
        return facilityResponse;
    }

    @Override
    public FacilityDTO updateFacility(FacilityDTO facilityDTO) {
        // todo check if the facility exists
        Facility facility = facilityRepository.findById(facilityDTO.getFacilityId()).orElseThrow(
                () -> new ResourceNotFoundException("Facility", "id", facilityDTO.getFacilityId()));

        facility.setFacilityName(facilityDTO.getFacilityName());
        facility.setFacilityDescription(facilityDTO.getFacilityDescription());
        Facility updatedFacility = facilityRepository.save(facility);

        return mapToDTO(updatedFacility);
    }

    @Override
    public FacilityDTO getFacilityById(Long id) {
        Facility facility = facilityRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Facility", "id", id));

        return mapToDTO(facility);
    }

    @Override
    public void deleteFacility(Long id) {
        Facility facility = facilityRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Facility", "id", id));

        facilityRepository.delete(facility);
    }

    // todo: implement this method
    private FacilityDTO mapToDTO(Facility facility) {
        FacilityDTO facilityDTO = new FacilityDTO();

        facilityDTO.setFacilityId(facility.getFacilityId());
        facilityDTO.setFacilityName(facility.getFacilityName());
        facilityDTO.setFacilityDescription(facility.getFacilityDescription());
        return facilityDTO;
    }

    // todo: implement this method
    private Facility mapToEntity(FacilityDTO facilityDTO) {
        Facility facility = new Facility();

        facility.setFacilityId(facilityDTO.getFacilityId());
        facility.setFacilityName(facilityDTO.getFacilityName());
        facility.setFacilityDescription(facilityDTO.getFacilityDescription());
        return facility;
    }
}
