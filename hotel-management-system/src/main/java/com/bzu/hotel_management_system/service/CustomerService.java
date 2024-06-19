package com.bzu.hotel_management_system.service;

import com.bzu.hotel_management_system.DTO.CustomerDTO;

public interface CustomerService {

    CustomerDTO updateCustomerById(CustomerDTO customerDTO, Long id);

    CustomerDTO getCustomerById(Long id);

    CustomerDTO changePassword(String password, Long id);

    CustomerDTO getCustomerReservations(Long id);

    void deleteCustomer(Long id);
}
