package com.bzu.hotel_management_system.service.imp;

import com.bzu.hotel_management_system.DTO.UserDTO;
import com.bzu.hotel_management_system.entity.Role;
import com.bzu.hotel_management_system.entity.User;
import com.bzu.hotel_management_system.exception.ResourceNotFoundException;
import com.bzu.hotel_management_system.repository.UserRepository;
import com.bzu.hotel_management_system.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImplementation implements UserService {
    private UserRepository userRepository;

    public UserServiceImplementation(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDTO addUser(UserDTO userDTO) {
        // check if the user already exists
        userRepository.findById(userDTO.getUserId()).orElseThrow(
                () -> new ResourceNotFoundException("User", "id", userDTO.getUserId()));

        User user = mapToEntity(userDTO);
        User newUser = userRepository.save(user);

        UserDTO userResponse = mapToDTO(newUser);
        return userResponse;
    }

    @Override
    public UserDTO updateUser(UserDTO userDTO, Long id) {
        // check if the user exists
        User user = userRepository.findById(userDTO.getUserId()).orElseThrow(
                () -> new ResourceNotFoundException("User", "id", userDTO.getUserId()));

        user.setUsername(userDTO.getUsername());
        user.setPassword(userDTO.getPassword());
        user.setRole(Role.valueOf(userDTO.getRole()));
        User updatedUser = userRepository.save(user);

        return mapToDTO(updatedUser);
    }

    @Override
    public UserDTO getUserById(Long id) {
        User user = userRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("User", "id", id));

        return mapToDTO(user);
    }

    @Override
    public void deleteUser(Long id) {
        User user = userRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("User", "id", id));

        userRepository.delete(user);
    }

    private UserDTO mapToDTO(User user) {
        UserDTO userDTO = new UserDTO();

        userDTO.setUserId(user.getUserId());
        userDTO.setUsername(user.getUsername());
        userDTO.setPassword(user.getPassword());
        userDTO.setRole(String.valueOf(user.getRole()));

        return userDTO;
    }

    private User mapToEntity(UserDTO userDTO) {
        User user = new User();

        user.setUserId(userDTO.getUserId());
        user.setUsername(userDTO.getUsername());
        user.setPassword(userDTO.getPassword());
        user.setRole(Role.valueOf(userDTO.getRole()));

        return user;
    }

}
