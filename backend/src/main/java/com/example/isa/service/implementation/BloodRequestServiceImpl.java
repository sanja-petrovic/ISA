package com.example.isa.service.implementation;

import com.example.isa.dto.BloodRequestDto;
import com.example.isa.dto.BloodRequestResponseDto;
import com.example.isa.kafka.Producer;
import com.example.isa.model.BloodBank;
import com.example.isa.model.BloodRequest;
import com.example.isa.model.BloodRequestStatus;
import com.example.isa.model.BloodType;
import com.example.isa.repository.BloodRequestRepository;
import com.example.isa.service.interfaces.BloodBankService;
import com.example.isa.service.interfaces.BloodRequestService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

@Service
public class BloodRequestServiceImpl implements BloodRequestService {

    private final BloodBankService bloodBankService;
    private final Producer producer;
    private final BloodRequestRepository bloodRequestRepository;

    public BloodRequestServiceImpl(BloodBankService bloodBankService, Producer producer, BloodRequestRepository bloodRequestRepository) {
        this.bloodBankService = bloodBankService;
        this.producer = producer;
        this.bloodRequestRepository = bloodRequestRepository;
    }

    @Override
    public void handleBloodRequest(BloodRequestDto bloodRequestDto) throws JsonProcessingException {
        BloodBank bloodBank = bloodBankService.findByTitle((bloodRequestDto.getBank()));
        BloodType bloodType = BloodType.valueOf(bloodRequestDto.getBloodType() + "_" + bloodRequestDto.getRhFactor());
        if(bloodBank != null) {
            boolean updated = bloodBankService.updateBloodSupplies(bloodBank, bloodType, bloodRequestDto.getAmount());
            this.save(bloodRequestDto, bloodType, bloodBank, updated);
            this.respond(bloodRequestDto.getId(), updated ? "Approved" : "Rejected: The bank could not fulfill this request due to low supplies.");
        } else {
            this.respond(bloodRequestDto.getId(), "Rejected: Bank does not exist.");
        }
    }

    private void respond(UUID requestId, String status) throws JsonProcessingException {
        BloodRequestResponseDto response = BloodRequestResponseDto.builder()
                .requestId(requestId)
                .status(status)
                .build();
        producer.send(response);
    }

    public void save(BloodRequestDto bloodRequestDto, BloodType bloodType, BloodBank bloodBank, boolean updated) {
        BloodRequest bloodRequest = BloodRequest.builder()
                .id(bloodRequestDto.getId())
                .bloodType(bloodType)
                .amount(bloodRequestDto.getAmount())
                .bloodBank(bloodBank)
                .receivedDate(new Date(System.currentTimeMillis()))
                .sendOnDate(new Date(bloodRequestDto.getSendOnDate())) //TODO: parse date properly
                .status(updated ? BloodRequestStatus.APPROVED : BloodRequestStatus.REJECTED)
                .build();
        bloodRequestRepository.save(bloodRequest);
    }
}
