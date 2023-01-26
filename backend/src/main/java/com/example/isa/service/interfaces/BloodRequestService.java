package com.example.isa.service.interfaces;

import com.example.isa.dto.BloodRequestDto;
import com.example.isa.dto.BloodSupplyDto;
import com.example.isa.model.BloodRequest;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.text.ParseException;
import java.util.List;
import java.util.UUID;

public interface BloodRequestService {
    void handleDoctorRequest(BloodRequestDto bloodRequestDto) throws JsonProcessingException, ParseException;
    BloodSupplyDto handleManagerRequest(BloodRequestDto bloodRequestDto) throws ParseException, JsonProcessingException;
    List<BloodRequest> getAllApproved();
    BloodRequest getById(UUID id);
}
