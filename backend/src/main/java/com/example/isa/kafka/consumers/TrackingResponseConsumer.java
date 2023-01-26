package com.example.isa.kafka.consumers;

import com.example.isa.dto.BloodRequestResponseDto;
import com.example.isa.dto.locator.TrackingResponseDto;
import com.example.isa.kafka.Producer;
import com.example.isa.model.locator.TrackingRequestStatus;
import com.example.isa.service.interfaces.TrackingRequestService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Slf4j
@Component
public class TrackingResponseConsumer {
    private final ObjectMapper objectMapper;
    private final TrackingRequestService trackingRequestService;
    private final Producer producer;
    private boolean started = false;

    public TrackingResponseConsumer(ObjectMapper objectMapper, TrackingRequestService trackingRequestService, Producer producer) {
        this.objectMapper = objectMapper;
        this.trackingRequestService = trackingRequestService;
        this.producer = producer;
    }

    @KafkaListener(topics = "tracking.response.topic", groupId = "locator")
    public void consumeMessage(String message) throws JsonProcessingException {
        log.info("message consumed {}", message);
        TrackingResponseDto dto = objectMapper.readValue(message, TrackingResponseDto.class);
        trackingRequestService.updateStatus(dto.id(), TrackingRequestStatus.valueOf(dto.status()));
        if(!started) {
            producer.send(new BloodRequestResponseDto(dto.id(), "TO_BE_SENT"));
            started = true;
        }
        if(Objects.equals(dto.status(), "COMPLETED")) {
            producer.send(new BloodRequestResponseDto(dto.id(), "FULFILLED"));
        }
    }
}
