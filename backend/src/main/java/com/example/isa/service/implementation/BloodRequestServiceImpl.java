package com.example.isa.service.implementation;

import com.example.isa.dto.BloodRequestDto;
import com.example.isa.dto.BloodRequestResponseDto;
import com.example.isa.kafka.Producer;
import com.example.isa.model.BloodBank;
import com.example.isa.model.BloodType;
import com.example.isa.service.interfaces.BloodBankService;
import com.example.isa.service.interfaces.BloodRequestService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.stereotype.Service;

@Service
public class BloodRequestServiceImpl implements BloodRequestService {

    private final BloodBankService bloodBankService;
    private final BloodRequestService bloodRequestService;
    private final Producer producer;

    public BloodRequestServiceImpl(BloodBankService bloodBankService, BloodRequestService bloodRequestService, Producer producer) {
        this.bloodBankService = bloodBankService;
        this.bloodRequestService = bloodRequestService;
        this.producer = producer;
    }


    @Override
    public void handleBloodRequest(BloodRequestDto bloodRequestDto) throws JsonProcessingException {
        BloodBank bloodBank = bloodBankService.findByTitle((bloodRequestDto.getBank()));
        BloodType bloodType = BloodType.valueOf(bloodRequestDto.getBloodType() + "_" + bloodRequestDto.getRhFactor());
        if(bloodBank != null) {
            BloodRequestResponseDto response = BloodRequestResponseDto.builder()
                    .requestId(bloodRequestDto.getId())
                    .status(bloodBankService.updateBloodSupplies(bloodBank, bloodType, bloodRequestDto.getAmount()) ? "APPROVED" : "REJECTED: Bank could not fulfill the request at the moment.")
                    .build();
            producer.send(response);
        } else {
            BloodRequestResponseDto response = BloodRequestResponseDto.builder()
                    .requestId(bloodRequestDto.getId())
                    .status("REJECTED: Bank does not exist.")
                    .build();
            producer.send(response);
        }
    }
}
