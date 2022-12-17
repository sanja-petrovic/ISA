package com.example.isa.scheduler;

import com.example.isa.dto.BloodRequestResponseDto;
import com.example.isa.dto.BloodSupplyDto;
import com.example.isa.kafka.Producer;
import com.fasterxml.jackson.core.JsonProcessingException;

public class ScheduledMessageSender implements Runnable {

    private final Producer producer;
    private final BloodSupplyDto message;
    public ScheduledMessageSender(Producer producer, BloodSupplyDto message) {
        this.producer = producer;
        this.message = message;
    }

    @Override
    public void run() {
        try {
            this.producer.send(message);
            BloodRequestResponseDto response = BloodRequestResponseDto.builder().requestId(message.getRequestId()).status("FULFILLED").build();
            this.producer.send(response);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}