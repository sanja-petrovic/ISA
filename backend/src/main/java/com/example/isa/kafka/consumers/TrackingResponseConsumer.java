package com.example.isa.kafka.consumers;

import com.example.isa.dto.locator.TrackingRequestDto;
import com.example.isa.dto.locator.TrackingResponseDto;
import com.example.isa.model.locator.TrackingRequest;
import com.example.isa.model.locator.TrackingRequestStatus;
import com.example.isa.service.interfaces.TrackingRequestService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;

@Slf4j
public class TrackingResponseConsumer {
    private final ObjectMapper objectMapper;
    private final TrackingRequestService trackingRequestService;

    public TrackingResponseConsumer(ObjectMapper objectMapper, TrackingRequestService trackingRequestService) {
        this.objectMapper = objectMapper;
        this.trackingRequestService = trackingRequestService;
    }

    @KafkaListener(topics = "tracking.response.topic", groupId = "locator")
    public void consumeMessage(String message) throws JsonProcessingException {
        log.info("message consumed {}", message);
        TrackingResponseDto dto = objectMapper.readValue(message, TrackingResponseDto.class);
        trackingRequestService.updateStatus(dto.id(), TrackingRequestStatus.valueOf(dto.status()));
    }
}
