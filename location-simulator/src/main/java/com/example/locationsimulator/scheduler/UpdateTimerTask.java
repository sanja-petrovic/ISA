package com.example.locationsimulator.scheduler;

import com.example.locationsimulator.communications.Producer;
import com.example.locationsimulator.dto.LocationUpdateDto;
import com.example.locationsimulator.dto.TrackingResponseDto;
import com.example.locationsimulator.model.Location;
import com.example.locationsimulator.model.TrackingRequest;
import com.example.locationsimulator.model.TrackingRequestStatus;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.List;
import java.util.TimerTask;

public class UpdateTimerTask extends TimerTask {
    private final TrackingRequest trackingRequest;
    private final Producer producer;
    private final List<Location> coordinates;
    private boolean started;

    public UpdateTimerTask(TrackingRequest trackingRequest, Producer producer, List<Location> coordinates) {
        this.trackingRequest = trackingRequest;
        this.producer = producer;
        this.coordinates = coordinates;
        started = false;
    }

    @Override
    public void run() {
        if(!started) {
            this.start();
        }
        Location location = coordinates.get(0);
        coordinates.subList(0, Math.min(coordinates.size(), trackingRequest.getFrequencyInSeconds())).clear();
        try {
            producer.send(new LocationUpdateDto(trackingRequest.getId(), location.getLatitude(), location.getLongitude()));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        if(coordinates.size() == 0) {
            this.end();
        }
    }

    public void start() {
        this.started = true;
        try {
            producer.send(new TrackingResponseDto(trackingRequest.getId(), TrackingRequestStatus.STARTED.toString()));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public void end() {
        try {
            producer.send(new TrackingResponseDto(trackingRequest.getId(), TrackingRequestStatus.COMPLETED.toString()));
            this.cancel();
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
