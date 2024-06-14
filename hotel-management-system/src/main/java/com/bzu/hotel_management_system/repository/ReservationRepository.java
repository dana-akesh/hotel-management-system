package com.bzu.hotel_management_system.repository;

import com.bzu.hotel_management_system.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    Optional<Reservation> findByDate(String date);
    Optional<Reservation> findByCustomerName(String customerName);

}
