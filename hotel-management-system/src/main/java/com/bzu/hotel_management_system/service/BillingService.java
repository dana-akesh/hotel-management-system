package com.bzu.hotel_management_system.service;

import com.bzu.hotel_management_system.DTO.BillingDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface BillingService {

    List<BillingDTO> getAllBillings();

    BillingDTO addBilling(BillingDTO billingDTO);

    BillingDTO getBillingById(Long id);

    BillingDTO updateBilling(BillingDTO billingDTO);

    void deleteBilling(Long id);

}
