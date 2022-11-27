package com.example.isa.kafka;

import com.example.isa.dto.BloodRequestDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class Consumer {
    private static final String topic = "blood.requests.topic";
    private final ObjectMapper objectMapper;

    public Consumer(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
        this.objectMapper.configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true);
    }

    @KafkaListener(topics = topic, groupId = "bloodRequests")
    public void consumeMessage(String message) throws JsonProcessingException {
        log.info("message consumed {}", message);
    }
}
