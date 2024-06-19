package com.bzu.hotel_management_system.repository;

import com.bzu.hotel_management_system.entity.Billing;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BillingRepository extends JpaRepository<Billing, Long> {
}
