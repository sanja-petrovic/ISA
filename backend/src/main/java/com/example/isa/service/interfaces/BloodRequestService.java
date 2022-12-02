package com.example.isa.service.interfaces;

import com.example.isa.dto.BloodRequestDto;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.text.ParseException;

public interface BloodRequestService {
    void handleBloodRequest(BloodRequestDto bloodRequestDto) throws JsonProcessingException, ParseException;
    void test();
}
