package com.bzu.hotel_management_system.service.imp;

import com.bzu.hotel_management_system.DTO.RoomDTO;
import com.bzu.hotel_management_system.entity.Room;
import com.bzu.hotel_management_system.exception.ResourceNotFoundException;
import com.bzu.hotel_management_system.repository.RoomRepository;
import com.bzu.hotel_management_system.service.RoomService;

public class RoomServiceImplementation implements RoomService {
    private RoomRepository roomRepository;

    public RoomServiceImplementation(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    @Override
    public RoomDTO getRoomById(Long id) {
        Room room = roomRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Room", "id", id));

        return mapToDTO(room);
    }

    @Override
    public RoomDTO addRoom(RoomDTO roomDTO) {
        Room room = mapToEntity(roomDTO);
        Room newRoom = roomRepository.save(room);

        RoomDTO roomResponse = mapToDTO(newRoom);
        return roomResponse;
    }

    @Override
    public RoomDTO updateRoom(RoomDTO roomDTO) {
        // todo check if the room exists
        Room room = roomRepository.findById(roomDTO.getRoomId()).orElseThrow(
                () -> new ResourceNotFoundException("Room", "id", roomDTO.getRoomId()));

        // check what values are being updated
        if (roomDTO.getCapacity() != 0) {
            room.setCapacity(roomDTO.getCapacity());
        }
        if (roomDTO.getPrice() != 0) {
            room.setPrice(roomDTO.getPrice());
        }
        if (roomDTO.getFeatures() != null) {
            room.setFeatures(roomDTO.getFeatures());
        }
        if (roomDTO.getFeatures() != null) {
            room.setFeatures(roomDTO.getFeatures());
        }
        if (roomDTO.getRoomId() != 0) {
            room.setRoomId(roomDTO.getRoomId());
        }


        Room updatedRoom = roomRepository.save(room);

        return mapToDTO(updatedRoom);
    }

    @Override
    public void deleteRoom(Long id) {
        Room room = roomRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Room", "id", id));

        roomRepository.delete(room);
    }

    private RoomDTO mapToDTO(Room room) {
        RoomDTO roomDTO = new RoomDTO();

        roomDTO.setRoomId(room.getRoomId());
        roomDTO.setCapacity(room.getCapacity());
        roomDTO.setPrice(room.getPrice());
        roomDTO.setFeatures(room.getFeatures());
        roomDTO.setType(room.getType());

        return roomDTO;
    }

    private Room mapToEntity(RoomDTO roomDTO) {
        Room room = new Room();

        room.setRoomId(roomDTO.getRoomId());
        room.setCapacity(roomDTO.getCapacity());
        room.setPrice(roomDTO.getPrice());
        room.setFeatures(roomDTO.getFeatures());
        room.setType(roomDTO.getType());
        //room.setFacility(roomDTO.getFacilityId());

        return room;
    }
}
