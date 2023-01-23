package com.example.locationsimulator.service;

import com.example.locationsimulator.model.TrackingRequest;

import java.util.UUID;

public interface TrackingRequestService {
    TrackingRequest getById(UUID id);
    void create(TrackingRequest trackingRequest);

}
