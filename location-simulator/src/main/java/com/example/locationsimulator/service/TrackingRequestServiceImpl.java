package com.example.locationsimulator.service;

import com.example.locationsimulator.communications.Producer;
import com.example.locationsimulator.model.TrackingRequest;
import com.example.locationsimulator.repository.TrackingRequestRepository;
import com.example.locationsimulator.scheduler.UpdateScheduler;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class TrackingRequestServiceImpl implements TrackingRequestService {
    private final TrackingRequestRepository repository;
    private final UpdateScheduler updateScheduler;
    private final Producer producer;

    public TrackingRequestServiceImpl(TrackingRequestRepository repository, UpdateScheduler updateScheduler, Producer producer) {
        this.repository = repository;
        this.updateScheduler = updateScheduler;
        this.producer = producer;
    }

    @Override
    public TrackingRequest getById(UUID id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public void create(TrackingRequest trackingRequest) {
        this.repository.save(trackingRequest);
        updateScheduler.scheduleUpdate(trackingRequest, producer);
    }
}
