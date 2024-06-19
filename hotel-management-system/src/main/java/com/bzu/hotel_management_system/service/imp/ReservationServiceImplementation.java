package com.bzu.hotel_management_system.service.imp;

import com.bzu.hotel_management_system.DTO.ReservationDTO;
import com.bzu.hotel_management_system.entity.Reservation;
import com.bzu.hotel_management_system.entity.User;
import com.bzu.hotel_management_system.exception.ResourceNotFoundException;
import com.bzu.hotel_management_system.repository.ReservationRepository;
import com.bzu.hotel_management_system.repository.UserRepository;
import com.bzu.hotel_management_system.service.ReservationService;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReservationServiceImplementation implements ReservationService {
    private ReservationRepository reservationRepository;
    private UserRepository userRepository;

    public ReservationServiceImplementation(ReservationRepository reservationRepository, UserRepository userRepository) {
        this.reservationRepository = reservationRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<ReservationDTO> getAllReservations() {
        List<Reservation> reservations = reservationRepository.findAll();
        return reservations.stream().map(this::mapToDTO).toList();
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
        User user = getCurrentUser();
        if (!user.getRole().equals("ADMIN") && !user.getUserId().equals(reservationDTO.getCustomer().getUserId())) {
            throw new AccessDeniedException("You do not have permission to update this customer");
        }
        // todo check if the reservation exists
        Reservation reservation = reservationRepository.findById(reservationDTO.getReservationId()).orElseThrow(
                () -> new ResourceNotFoundException("Reservation", "id", reservationDTO.getReservationId()));

        reservation.setCustomer(reservationDTO.getCustomer());
        //todo reservation.setReservationRooms(reservationDTO.getReservationRooms());
        reservation.setReservationRoom(reservationDTO.getReservationRoom());
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
        User user = getCurrentUser();
        if (!user.getRole().equals("ADMIN") && !user.getUserId().equals(reservation.getCustomer().getUserId())) {
            throw new AccessDeniedException("You do not have permission to view this customer");
        }
        return mapToDTO(reservation);
    }

    @Override
    public void deleteReservation(Long id) {
        User user = getCurrentUser();
        if (!user.getRole().equals("ADMIN") && !user.getUserId().equals(id)) {
            throw new AccessDeniedException("You do not have permission to delete this customer");
        }
        Reservation reservation = reservationRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Reservation", "id", id));

        reservationRepository.delete(reservation);
    }

    @Override
    public ReservationDTO getReservationByUserId(Long id) {
        User user = getCurrentUser();
        if (!user.getRole().equals("ADMIN") && !user.getUserId().equals(id)) {
            throw new AccessDeniedException("You do not have permission to view this customer");
        }
        Reservation reservation = reservationRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Reservation", "id", id));

        return mapToDTO(reservation);
    }

    @Override
    public ReservationDTO getReservationByUserName(String name) {
        // todo: check if this works
        Reservation reservation = reservationRepository.findByCustomerName(name).orElseThrow(
                () -> new ResourceNotFoundException("Reservation", "name", name));
        User user = getCurrentUser();
        if (!user.getRole().equals("ADMIN") && !user.getUserId().equals(reservation.getCustomer().getUserId())) {
            throw new AccessDeniedException("You do not have permission to view this customer");
        }
        return mapToDTO(reservation);
    }

    @Override
    public ReservationDTO getReservationByDate(String date) {
        Reservation reservation = reservationRepository.findByDate(date).orElseThrow(
                () -> new ResourceNotFoundException("Reservation", "date", date));
        User user = getCurrentUser();
        if (!user.getRole().equals("ADMIN") && !user.getUserId().equals(reservation.getCustomer().getUserId())) {
            throw new AccessDeniedException("You do not have permission to view this customer");
        }
        return mapToDTO(reservation);
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

    @Override
    public void approveCheckIn(Long id) {
        Reservation reservation = reservationRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Reservation", "id", id));

        if(!reservation.getIsCheckIn()){
            reservation.setIsCheckIn(true);
            reservationRepository.save(reservation);
        } else {
            throw new IllegalStateException("Reservation is already checked in");
        }
    }

    @Override
    public void approveCheckOut(Long id) {
        Reservation reservation = reservationRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Reservation", "id", id));

        if(!reservation.getIsCheckOut()){
            reservation.setIsCheckOut(true);
            reservationRepository.save(reservation);
        } else {
            throw new IllegalStateException("Reservation is already checked OUT");
        }
    }

    private ReservationDTO mapToDTO(Reservation reservation) {
        ReservationDTO reservationDTO = new ReservationDTO();

        reservationDTO.setReservationId(reservation.getReservationId());
        reservationDTO.setCustomer(reservation.getCustomer());
        //todo reservationDTO.setReservationRooms(reservation.getReservationRooms());
        reservationDTO.setDate(reservation.getDate());
        reservationDTO.setStatus(reservation.getStatus());
        reservationDTO.setIsCheckIn(reservation.getIsCheckIn());
        reservationDTO.setIsCheckOut(reservation.getIsCheckOut());

        return reservationDTO;
    }

    private Reservation mapToEntity(ReservationDTO reservationDTO) {
        Reservation reservation = new Reservation();

        reservation.setReservationId(reservationDTO.getReservationId());
        reservation.setCustomer(reservationDTO.getCustomer());
        //todo reservation.setReservationRooms(reservationDTO.getReservationRooms());
        reservation.setDate(reservationDTO.getDate());
        reservation.setStatus(reservationDTO.getStatus());
        reservation.setIsCheckIn(reservationDTO.getIsCheckIn());
        reservation.setIsCheckOut(reservationDTO.getIsCheckOut());

        return reservation;
    }

    private User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        return userRepository.findByUsername(username).orElseThrow(
                () -> new UsernameNotFoundException("User not found"));
    }
}
