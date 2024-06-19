package com.bzu.hotel_management_system.service;

import com.bzu.hotel_management_system.DTO.RoomDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface RoomService {
    // get all rooms
    List<RoomDTO> getAllRooms(int page, int size);

    RoomDTO getRoomById(Long id);

    RoomDTO addRoom(RoomDTO roomDTO);

    RoomDTO updateRoom(RoomDTO roomDTO);

    void deleteRoom(Long id);
}
