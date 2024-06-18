package com.bzu.hotel_management_system.service.imp;

import com.bzu.hotel_management_system.DTO.CustomerDTO;
import com.bzu.hotel_management_system.entity.Customer;
import com.bzu.hotel_management_system.exception.ResourceNotFoundException;
import com.bzu.hotel_management_system.repository.CustomerRepository;
import com.bzu.hotel_management_system.service.CustomerService;
import org.springframework.stereotype.Service;

@Service
public class CustomerServiceImplementation implements CustomerService {
    private CustomerRepository customerRepository;

    public CustomerServiceImplementation(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public CustomerDTO addCustomer(CustomerDTO customerDTO) {

        Customer customer = mapToEntity(customerDTO);
        Customer newCustomer = customerRepository.save(customer);

        CustomerDTO customerResponse = mapToDTO(newCustomer);
        return customerResponse;
    }

    @Override
    public CustomerDTO updateCustomerById(CustomerDTO customerDTO, Long id) {
        // todo check if the customer exists
        Customer customer = customerRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Customer", "id", id));

        customer.setName(customerDTO.getName());
        Customer updatedCustomer = customerRepository.save(customer);

        return mapToDTO(updatedCustomer);
    }

    @Override
    public CustomerDTO getCustomerById(Long id) {
        Customer customer = customerRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Customer", "id", id));

        return mapToDTO(customer);
    }

    // TODO: Add get customer by name, Date of Birth

    @Override
    public CustomerDTO changePassword(String password, Long id) {
        Customer customer = customerRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Customer", "id", id));

        customer.setPassword(password);
        Customer updatedCustomer = customerRepository.save(customer);

        return mapToDTO(updatedCustomer);
    }

    @Override
    public CustomerDTO getCustomerReservations(Long id) {
        Customer customer = customerRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Customer", "id", id));

        return mapToDTO(customer);
    }

    @Override
    public void deleteCustomer(Long id) {
        Customer customer = customerRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Customer", "id", id));

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
        customer.setUserId(customerDTO.getUserId());
        customer.setPassword(customerDTO.getPassword());
        customer.setReservations(customerDTO.getReservations());

        return customer;
    }
}
