package com.example.locationsimulator.communications;

import com.example.locationsimulator.dto.TrackingRequestDto;
import com.example.locationsimulator.model.*;
import com.example.locationsimulator.service.TrackingRequestService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
public class TrackingRequestConsumer {
    private final ObjectMapper objectMapper;
    private final TrackingRequestService trackingRequestService;

    public TrackingRequestConsumer(ObjectMapper objectMapper, TrackingRequestService trackingRequestService) {
        this.objectMapper = objectMapper;
        this.trackingRequestService = trackingRequestService;
    }

    @KafkaListener(topics = "tracking.request.topic", groupId = "locator")
    public void consume(String message) throws JsonProcessingException {
        log.info("message consumed {}", message);
        TrackingRequestDto dto = objectMapper.readValue(message, TrackingRequestDto.class);
        TrackingRequest trackingRequest = TrackingRequest.builder()
                .id(dto.id())
                .status(TrackingRequestStatus.RECEIVED)
                .timestamp(new Date())
                .start(new Location(dto.latitudeStart(), dto.longitudeStart()))
                .end(new Location(dto.latitudeEnd(), dto.longitudeEnd()))
                .updateFrequency(new Frequency(dto.frequencyTimeValue(), TimeUnit.valueOf(dto.frequencyTimeUnit())))
                .build();
        trackingRequestService.create(trackingRequest);
    }
}
