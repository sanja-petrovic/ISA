package com.example.locationsimulator.scheduler;

import com.example.locationsimulator.communications.Producer;
import com.example.locationsimulator.dto.LocationUpdateDto;
import com.example.locationsimulator.model.TrackingRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.web.client.RestTemplate;

public class UpdateSender implements Runnable {
    private final TrackingRequest trackingRequest;
    private final Producer producer;

    public UpdateSender(TrackingRequest trackingRequest, Producer producer) {
        this.trackingRequest = trackingRequest;
        this.producer = producer;
    }

    @Override
    public void run() {
        try {
            RestTemplate restTemplate = new RestTemplate();
            StringBuilder uri = new StringBuilder("https://routing.openstreetmap.de/routed-car/route/v1/driving/");
            uri.append(trackingRequest.getStart().getLatitude());
            uri.append(",");
            uri.append(trackingRequest.getStart().getLongitude());
            uri.append(";");
            uri.append(trackingRequest.getEnd().getLatitude());
            uri.append(",");
            uri.append(trackingRequest.getEnd().getLongitude());
            uri.append("?geometries=geojson&overview=false&alternatives=true&steps=true");
            String result = restTemplate.getForObject(uri.toString(), String.class);
            producer.send(new LocationUpdateDto(trackingRequest.getId(), 0, 0));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}