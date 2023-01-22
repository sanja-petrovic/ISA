package com.example.locationsimulator.service;

import com.example.locationsimulator.repository.TrackingRequestRepository;
import org.springframework.stereotype.Service;

@Service
public class TrackingRequestServiceImpl implements TrackingRequestService {
    private final TrackingRequestRepository repository;

    public TrackingRequestServiceImpl(TrackingRequestRepository repository) {
        this.repository = repository;
    }
}
