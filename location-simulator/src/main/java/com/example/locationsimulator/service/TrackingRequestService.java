package com.example.locationsimulator.service;

import com.example.locationsimulator.model.TrackingRequest;

import java.util.UUID;

public interface TrackingRequestService {
    void save(TrackingRequest trackingRequest);
    TrackingRequest getById(UUID id);

}
