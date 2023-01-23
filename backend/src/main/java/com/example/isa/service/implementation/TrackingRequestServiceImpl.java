package com.example.isa.service.implementation;

import com.example.isa.model.locator.TrackingRequest;
import com.example.isa.model.locator.TrackingRequestStatus;
import com.example.isa.repository.TrackingRequestRepository;
import com.example.isa.service.interfaces.TrackingRequestService;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class TrackingRequestServiceImpl implements TrackingRequestService {
    private final TrackingRequestRepository trackingRequestRepository;

    public TrackingRequestServiceImpl(TrackingRequestRepository trackingRequestRepository) {
        this.trackingRequestRepository = trackingRequestRepository;
    }

    @Override
    public void updateStatus(UUID requestId, TrackingRequestStatus status) {
        TrackingRequest trackingRequest = trackingRequestRepository.getReferenceById(requestId);
        trackingRequest.setStatus(status);
        trackingRequestRepository.save(trackingRequest);
    }
}
