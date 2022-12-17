package com.example.isa.kafka.consumers;

import com.example.isa.dto.BloodRequestDto;
import com.example.isa.service.interfaces.BloodRequestService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.text.ParseException;

@Component
@Slf4j
public class BloodRequestConsumer {
    private final ObjectMapper objectMapper;
    private final BloodRequestService bloodRequestService;

    public BloodRequestConsumer(ObjectMapper objectMapper, BloodRequestService bloodRequestService) {
        this.objectMapper = objectMapper;
        this.bloodRequestService = bloodRequestService;
    }

    /*@KafkaListener(topics = "blood.requests.topic", groupId = "bloodRequests")
    public void consumeMessage(String message) throws JsonProcessingException, ParseException {
        log.info("message consumed {}", message);
        bloodRequestService.handleDoctorRequest(objectMapper.readValue(message, BloodRequestDto.class));
    }*/
}
