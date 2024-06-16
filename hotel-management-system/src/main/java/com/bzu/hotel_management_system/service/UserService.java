package com.bzu.hotel_management_system.service;


import com.bzu.hotel_management_system.DTO.UserDTO;
import org.springframework.stereotype.Service;

@Service
public interface UserService {

    UserDTO addUser(UserDTO userDTO);

    UserDTO updateUser(UserDTO userDTO, Long id);

    UserDTO getUserById(Long id);

    void deleteUser(Long id);
}
