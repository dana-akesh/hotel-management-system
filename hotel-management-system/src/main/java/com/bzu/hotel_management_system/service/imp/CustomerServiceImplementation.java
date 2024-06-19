package com.bzu.hotel_management_system.service.imp;

import com.bzu.hotel_management_system.DTO.CustomerDTO;
import com.bzu.hotel_management_system.entity.Customer;
import com.bzu.hotel_management_system.entity.User;
import com.bzu.hotel_management_system.exception.ResourceNotFoundException;
import com.bzu.hotel_management_system.repository.CustomerRepository;
import com.bzu.hotel_management_system.repository.UserRepository;
import com.bzu.hotel_management_system.service.CustomerService;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomerServiceImplementation implements CustomerService {
    private final CustomerRepository customerRepository;
    private final UserRepository userRepository;

    public CustomerServiceImplementation(CustomerRepository customerRepository, UserRepository userRepository) {
        this.customerRepository = customerRepository;
        this.userRepository = userRepository;
    }

    @Override
    public CustomerDTO updateCustomerById(CustomerDTO customerDTO, Long id) {
        Customer customer = customerRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Customer", "id", id));

        User user = getCurrentUser();
        if (!user.getRole().equals("ADMIN") && !user.getUserId().equals(customer.getUserId())) {
            throw new AccessDeniedException("You do not have permission to update this customer");
        }

        customer.setName(customerDTO.getName());
        Customer updatedCustomer = customerRepository.save(customer);
        return mapToDTO(updatedCustomer);
    }

    @Override
    public CustomerDTO getCustomerById(Long id) {
        Customer customer = customerRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Customer", "id", id));

        User user = getCurrentUser();
        if (!user.getRole().equals("ADMIN") && !user.getUserId().equals(customer.getUserId())) {
            throw new AccessDeniedException("You do not have permission to view this customer");
        }

        return mapToDTO(customer);
    }

    @Override
    public CustomerDTO changePassword(String password, Long id) {
        Customer customer = customerRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Customer", "id", id));

        User user = getCurrentUser();
        if (!user.getRole().equals("ADMIN") && !user.getUserId().equals(customer.getUserId())) {
            throw new AccessDeniedException("You do not have permission to change the password for this customer");
        }

        customer.setPassword(password);
        Customer updatedCustomer = customerRepository.save(customer);
        return mapToDTO(updatedCustomer);
    }

    @Override
    public CustomerDTO getCustomerReservations(Long id) {
        Customer customer = customerRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Customer", "id", id));

        User user = getCurrentUser();
        if (!user.getRole().equals("ADMIN") && !user.getUserId().equals(customer.getUserId())) {
            throw new AccessDeniedException("You do not have permission to view reservations for this customer");
        }

        return mapToDTO(customer);
    }

    @Override
    public void deleteCustomer(Long id) {
        Customer customer = customerRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Customer", "id", id));

        User user = getCurrentUser();
        if (!user.getRole().equals("ADMIN")) {
            throw new AccessDeniedException("You do not have permission to delete this customer");
        }

        customerRepository.delete(customer);
    }

    private CustomerDTO mapToDTO(Customer customer) {
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setUsername(customer.getUsername());
        customerDTO.setUserId(customer.getUserId());
        customerDTO.setName(customer.getName());
        customerDTO.setPassword(customer.getPassword());
        customerDTO.setReservations(customer.getReservations());
        return customerDTO;
    }

    private Customer mapToEntity(CustomerDTO customerDTO) {
        Customer customer = new Customer();
        customer.setName(customerDTO.getName());
        customer.setUsername(customerDTO.getUsername());
        customer.setPassword(customerDTO.getPassword());

        User user = userRepository.findById(customerDTO.getUserId()).orElseThrow(
                () -> new ResourceNotFoundException("User", "id", customerDTO.getUserId()));
        customer.setUserId(user.getUserId());
        customer.setReservations(customerDTO.getReservations());
        return customer;
    }

    private User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        return userRepository.findByUsername(username).orElseThrow(
                () -> new UsernameNotFoundException("User not found"));
    }
}
