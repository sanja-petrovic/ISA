package com.example.locationsimulator.scheduler;

import com.example.locationsimulator.communications.Producer;
import com.example.locationsimulator.dto.LocationUpdateDto;
import com.example.locationsimulator.model.TrackingRequest;
import com.fasterxml.jackson.core.JsonProcessingException;

public class UpdateSender implements Runnable {
    private final TrackingRequest trackingRequest;
    private final Producer producer;

    public UpdateSender(TrackingRequest trackingRequest, Producer producer) {
        this.trackingRequest = trackingRequest;
        this.producer = producer;
    }

    @Override
    public void run() {
        //make request to api
        try {
            producer.send(new LocationUpdateDto(trackingRequest.getId(), 0, 0));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}