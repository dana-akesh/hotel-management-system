package com.bzu.hotel_management_system.service.imp;

import com.bzu.hotel_management_system.DTO.BillingDTO;
import com.bzu.hotel_management_system.entity.Billing;
import com.bzu.hotel_management_system.exception.ResourceNotFoundException;
import com.bzu.hotel_management_system.repository.BillingRepository;
import com.bzu.hotel_management_system.service.BillingService;

import java.util.List;

public class BillingServiceImplementation implements BillingService {
    private BillingRepository billingRepository;

    public BillingServiceImplementation(BillingRepository billingRepository) {
        this.billingRepository = billingRepository;
    }

    @Override
    public List<BillingDTO> getAllBillings() {
        List<Billing> billings = billingRepository.findAll();
        return billings.stream().map(this::mapToDTO).toList();
    }

    @Override
    public BillingDTO addBilling(BillingDTO billingDTO) {
        Billing billing = mapToEntity(billingDTO);
        Billing newBilling = billingRepository.save(billing);
        BillingDTO billingResponse = mapToDTO(newBilling);
        return billingResponse;
    }

    @Override
    public BillingDTO getBillingById(Long id) {
        Billing billing = billingRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Billing", "id", id));
        return mapToDTO(billing);
    }

    @Override
    public BillingDTO updateBilling(BillingDTO billingDTO) {
        Billing billing = billingRepository.findById(billingDTO.getId()).orElseThrow(
                () -> new ResourceNotFoundException("Billing", "id", billingDTO.getId()));
        billing.setCustomerId(billingDTO.getCustomerId());
        billing.setReservationId(billingDTO.getReservationId());
        billing.setAmount(billingDTO.getAmount());
        billing.setDate(billingDTO.getDate());
        billing.setPaid(billingDTO.isPaid());
        Billing updatedBilling = billingRepository.save(billing);
        return mapToDTO(updatedBilling);
    }

    @Override
    public void deleteBilling(Long id) {
        Billing billing = billingRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Billing", "id", id));
        billingRepository.delete(billing);
    }

    public BillingDTO mapToDTO(Billing billing) {
        BillingDTO billingDTO = new BillingDTO();
        billingDTO.setId(billing.getId());
        billingDTO.setCustomerId(billing.getCustomer().getUserId());
        billingDTO.setReservationId(billing.getReservation().getReservationId());
        billingDTO.setAmount(billing.getAmount());
        billingDTO.setDate(billing.getDate());
        billingDTO.setPaid(billing.isPaid());
        return billingDTO;
    }

    public Billing mapToEntity(BillingDTO billingDTO) {
        Billing billing = new Billing();
        billing.setId(billingDTO.getId());
        billing.setCustomerId(billingDTO.getCustomerId());
        billing.setReservationId(billingDTO.getReservationId());
        billing.setAmount(billingDTO.getAmount());
        billing.setDate(billingDTO.getDate());
        billing.setPaid(billingDTO.isPaid());
        return billing;
    }
}
