package com.bzu.hotel_management_system.service;

import com.bzu.hotel_management_system.DTO.RoomDTO;

public interface RoomService {
    RoomDTO getRoomById(Long id);

    RoomDTO addRoom(RoomDTO roomDTO);

    RoomDTO updateRoom(RoomDTO roomDTO);

    void deleteRoom(Long id);
}
