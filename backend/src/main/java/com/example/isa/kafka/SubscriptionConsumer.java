package com.example.isa.kafka;

import java.security.Provider.Service;
import java.text.ParseException;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.example.isa.dto.BloodSubscriptionSignUpDto;
import com.example.isa.service.interfaces.BloodSubscriptionService;
import com.example.isa.util.converters.SubscriptionSignUpConverter;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class SubscriptionConsumer {
	private final ObjectMapper objectMapper;
    private final BloodSubscriptionService bloodSubscriptionService;

    
    public SubscriptionConsumer(ObjectMapper objectMapper,BloodSubscriptionService bloodSubscriptionService) {
    	this.objectMapper = objectMapper;
    	this.bloodSubscriptionService = bloodSubscriptionService;
    }
    
    @KafkaListener(topics = "blood.subscriptions.topic", groupId = "bloodSubscriptions")
    void consumeMessage(String message) throws JsonProcessingException, ParseException{
    	log.info("message consumed {}", message);
    	BloodSubscriptionSignUpDto dto = objectMapper.readValue(message, BloodSubscriptionSignUpDto.class);
    	try{
    		bloodSubscriptionService.handleRegistration(dto);
    	}
    	catch(Exception e){
    		log.info(e.toString());
    		log.info("message discarded");
    	}
    }
}
