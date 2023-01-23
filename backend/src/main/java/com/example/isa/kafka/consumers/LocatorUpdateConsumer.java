package com.example.isa.kafka.consumers;

import com.example.isa.dto.locator.LocationUpdateDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;

@Slf4j
public class LocatorUpdateConsumer {
    private final ObjectMapper objectMapper;
    private final SimpMessagingTemplate simpMessagingTemplate;

    public LocatorUpdateConsumer(ObjectMapper objectMapper, SimpMessagingTemplate simpMessagingTemplate) {
        this.objectMapper = objectMapper;
        this.simpMessagingTemplate = simpMessagingTemplate;
    }

    @KafkaListener(topics = "location.update.topic", groupId = "locator")
    public void consumeMessage(String message) throws JsonProcessingException {
        log.info("message consumed {}", message);
        LocationUpdateDto dto = objectMapper.readValue(message, LocationUpdateDto.class);
        simpMessagingTemplate.convertAndSend("/location-update/" + dto.requestId(), dto);
    }
}