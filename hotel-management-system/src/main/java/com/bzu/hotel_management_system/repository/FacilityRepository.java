package com.bzu.hotel_management_system.repository;

import com.bzu.hotel_management_system.entity.Facility;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FacilityRepository extends JpaRepository<Facility, Long> {
}
