package com.bzu.hotel_management_system.service;


import com.bzu.hotel_management_system.DTO.UserDTO;

public interface UserService {

    UserDTO addUser(UserDTO userDTO);

    UserDTO updateUser(UserDTO userDTO);

    UserDTO getUserById(Long id);

    void deleteUser(Long id);
}
