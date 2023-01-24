package com.example.locationsimulator.service;

import com.example.locationsimulator.communications.Producer;
import com.example.locationsimulator.model.TrackingRequest;
import com.example.locationsimulator.scheduler.UpdateScheduler;
import org.springframework.stereotype.Service;

@Service
public class TrackingRequestServiceImpl implements TrackingRequestService {
    private final UpdateScheduler updateScheduler;
    private final Producer producer;

    public TrackingRequestServiceImpl(UpdateScheduler updateScheduler, Producer producer) {
        this.updateScheduler = updateScheduler;
        this.producer = producer;
    }

    @Override
    public void create(TrackingRequest trackingRequest) {
        updateScheduler.scheduleUpdate(trackingRequest, producer);
    }
}
