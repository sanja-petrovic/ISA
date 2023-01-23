package com.example.locationsimulator.service;

import com.example.locationsimulator.model.TrackingRequest;
import com.example.locationsimulator.repository.TrackingRequestRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class TrackingRequestServiceImpl implements TrackingRequestService {
    private final TrackingRequestRepository repository;

    public TrackingRequestServiceImpl(TrackingRequestRepository repository) {
        this.repository = repository;
    }

    @Override
    public void save(TrackingRequest trackingRequest) {
        repository.save(trackingRequest);
    }

    @Override
    public TrackingRequest getById(UUID id) {
        return repository.findById(id).orElse(null);
    }
}
