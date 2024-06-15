package com.bzu.hotel_management_system.service.imp;

import com.bzu.hotel_management_system.DTO.ReservationDTO;
import com.bzu.hotel_management_system.entity.Reservation;
import com.bzu.hotel_management_system.exception.ResourceNotFoundException;
import com.bzu.hotel_management_system.repository.ReservationRepository;
import com.bzu.hotel_management_system.service.ReservationService;

public class ReservationServiceImplementation implements ReservationService {
    private ReservationRepository reservationRepository;

    public ReservationServiceImplementation(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    @Override
    public ReservationDTO addReservation(ReservationDTO reservationDTO) {

            Reservation reservation = mapToEntity(reservationDTO);
            Reservation newReservation = reservationRepository.save(reservation);

            ReservationDTO reservationResponse = mapToDTO(newReservation);
            return reservationResponse;
    }

    @Override
    public ReservationDTO updateReservation(ReservationDTO reservationDTO) {
        // todo check if the reservation exists
        Reservation reservation = reservationRepository.findById(reservationDTO.getReservationId()).orElseThrow(
                () -> new ResourceNotFoundException("Reservation", "id", reservationDTO.getReservationId()));

        reservation.setCustomer(reservationDTO.getCustomer());
        reservation.setReservationRooms(reservationDTO.getReservationRooms());
        reservation.setReservationId(reservationDTO.getReservationId());
        reservation.setDate(reservationDTO.getDate());
        reservation.setStatus(reservationDTO.getStatus());
        Reservation updatedReservation = reservationRepository.save(reservation);

        return mapToDTO(updatedReservation);
    }

    @Override
    public ReservationDTO getReservationById(Long id) {
        Reservation reservation = reservationRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Reservation", "id", id));

        return mapToDTO(reservation);
    }

    @Override
    public void deleteReservation(Long id) {
        Reservation reservation = reservationRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Reservation", "id", id));

        reservationRepository.delete(reservation);
    }

    @Override
    public ReservationDTO getReservationByUserId(Long id) {
        return null;
    }

    @Override
    public ReservationDTO getReservationByUserName(String name) {
        // todo: check if this works
        Reservation reservation = reservationRepository.findByCustomerName(name).orElseThrow(
                () -> new ResourceNotFoundException("Reservation", "name", name));

        return mapToDTO(reservation);
    }

    @Override
    public ReservationDTO getReservationByDate(String date) {
        Reservation reservation = reservationRepository.findByDate(date).orElseThrow(
                () -> new ResourceNotFoundException("Reservation", "date", date));

        return mapToDTO(reservation);
    }

    @Override
    public void cancelReservation(Long id) {
        Reservation reservation = reservationRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Reservation", "id", id));

        reservation.setStatus("Pending Cancellation");
        reservationRepository.save(reservation);
    }

    @Override
    public void approveCancelReservation(Long id) {
        Reservation reservation = reservationRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Reservation", "id", id));

        if ("Pending Cancellation".equals(reservation.getStatus())) {
            reservationRepository.delete(reservation);
        } else {
            throw new IllegalStateException("Reservation is not pending cancellation");
        }
    }

    private ReservationDTO mapToDTO(Reservation reservation) {
        ReservationDTO reservationDTO = new ReservationDTO();

        reservationDTO.setReservationId(reservation.getReservationId());
        reservationDTO.setCustomer(reservation.getCustomer());
        reservationDTO.setReservationRooms(reservation.getReservationRooms());
        reservationDTO.setDate(reservation.getDate());
        reservationDTO.setStatus(reservation.getStatus());

        return reservationDTO;
    }

    private Reservation mapToEntity(ReservationDTO reservationDTO) {
        Reservation reservation = new Reservation();

        reservation.setReservationId(reservationDTO.getReservationId());
        reservation.setCustomer(reservationDTO.getCustomer());
        reservation.setReservationRooms(reservationDTO.getReservationRooms());
        reservation.setDate(reservationDTO.getDate());
        reservation.setStatus(reservationDTO.getStatus());

        return reservation;
    }

}