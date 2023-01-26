package com.example.locationsimulator.communications;

import com.example.locationsimulator.dto.LocationUpdateDto;
import com.example.locationsimulator.dto.TrackingResponseDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class Producer {
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    public Producer(KafkaTemplate<String, String> kafkaTemplate, ObjectMapper objectMapper) {
        this.kafkaTemplate = kafkaTemplate;
        this.objectMapper = objectMapper;
    }

    public void send(TrackingResponseDto dto) throws JsonProcessingException {
        String topic = "tracking.response.topic";
        String message = objectMapper.writeValueAsString(dto);
        kafkaTemplate.send(topic, message);
        log.info("Tracking response to request #{} sent: {}", dto.id(), message);
    }

    public void send(LocationUpdateDto dto) throws JsonProcessingException {
        String topic = "location.update.topic";
        String message = objectMapper.writeValueAsString(dto);
        kafkaTemplate.send(topic, message);
        log.info("[[#{}] Location: {}, {}", dto.requestId(), dto.latitude(), dto.longitude());
    }
}
