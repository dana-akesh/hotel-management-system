package com.bzu.hotel_management_system.repository;

import com.bzu.hotel_management_system.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
