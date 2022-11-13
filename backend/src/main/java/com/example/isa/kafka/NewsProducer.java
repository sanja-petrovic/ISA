package com.example.isa.kafka;

import com.example.isa.dto.NewsDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class NewsProducer {
    @Value("{news.topic}")
    private String topic;

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    public NewsProducer(KafkaTemplate<String, String> kafkaTemplate, ObjectMapper objectMapper) {
        this.kafkaTemplate = kafkaTemplate;
        this.objectMapper = objectMapper;
    }

    public void sendMessage(NewsDto newsDto) throws JsonProcessingException {
        String newsAsMessage = objectMapper.writeValueAsString(newsDto);
        kafkaTemplate.send(topic, newsAsMessage);
        log.info("news sent: {}", newsAsMessage);
    }
}
