package com.example.isa.service.implementation;

import com.example.isa.dto.locator.TrackingRequestDto;
import com.example.isa.kafka.Producer;
import com.example.isa.model.locator.TrackingRequest;
import com.example.isa.model.locator.TrackingRequestStatus;
import com.example.isa.repository.TrackingRequestRepository;
import com.example.isa.service.interfaces.TrackingRequestService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class TrackingRequestServiceImpl implements TrackingRequestService {
    private final TrackingRequestRepository trackingRequestRepository;
    private final Producer producer;

    public TrackingRequestServiceImpl(TrackingRequestRepository trackingRequestRepository, Producer producer) {
        this.trackingRequestRepository = trackingRequestRepository;
        this.producer = producer;
    }

    @Override
    public void updateStatus(UUID requestId, TrackingRequestStatus status) {
        TrackingRequest trackingRequest = trackingRequestRepository.getReferenceById(requestId);
        trackingRequest.setStatus(status);
        trackingRequestRepository.save(trackingRequest);
    }

    @Override
    @Transactional
    public void create(TrackingRequest trackingRequest) {
        trackingRequestRepository.save(trackingRequest);
        try {
            this.producer.send(new TrackingRequestDto(trackingRequest.getId(), trackingRequest.getUpdateFrequency().getValue(), trackingRequest.getUpdateFrequency().getUnit(), trackingRequest.getStart().getLatitude(), trackingRequest.getStart().getLongitude(), trackingRequest.getEnd().getLatitude(), trackingRequest.getEnd().getLongitude(), trackingRequest.getStatus().toString()));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
