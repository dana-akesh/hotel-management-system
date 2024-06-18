package com.bzu.hotel_management_system.service;

import com.bzu.hotel_management_system.DTO.EmployeeDTO;
import com.bzu.hotel_management_system.DTO.ReservationDTO;

import java.util.List;

public interface ReservationService {

    List<ReservationDTO> getAllReservations();

    ReservationDTO addReservation(ReservationDTO reservationDTO);

    ReservationDTO updateReservation(ReservationDTO reservationDTO);

    ReservationDTO getReservationById(Long id);

    void deleteReservation(Long id);

    ReservationDTO getReservationByUserId(Long id);

    ReservationDTO getReservationByUserName(String name);

    ReservationDTO getReservationByDate(String date);

    void approveCancelReservation(Long id);
}
