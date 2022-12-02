package com.example.isa.scheduler;

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
        System.out.println("It's me, hi");
        //this.producer.send(message);
    }
}