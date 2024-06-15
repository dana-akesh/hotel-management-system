package com.bzu.hotel_management_system.repository;

import com.bzu.hotel_management_system.entity.ReservationRoom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationRoomRepository extends JpaRepository<ReservationRoom, Long> {
}
