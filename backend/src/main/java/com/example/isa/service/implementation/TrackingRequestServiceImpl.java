package com.example.isa.service.implementation;

import com.example.isa.dto.locator.TrackingRequestDto;
import com.example.isa.kafka.Producer;
import com.example.isa.model.locator.TrackingRequest;
import com.example.isa.model.locator.TrackingRequestStatus;
import com.example.isa.repository.TrackingRequestRepository;
import com.example.isa.service.interfaces.TrackingRequestService;
import com.example.isa.util.converters.TrackingRequestConverter;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Service
public class TrackingRequestServiceImpl implements TrackingRequestService {
    private final TrackingRequestRepository trackingRequestRepository;
    private final Producer producer;
    private final TrackingRequestConverter trackingRequestConverter;

    public TrackingRequestServiceImpl(TrackingRequestRepository trackingRequestRepository, Producer producer, TrackingRequestConverter trackingRequestConverter) {
        this.trackingRequestRepository = trackingRequestRepository;
        this.producer = producer;
        this.trackingRequestConverter = trackingRequestConverter;
    }

    @Override
    public void updateStatus(UUID requestId, TrackingRequestStatus status) {
        Optional<TrackingRequest> trackingRequest = trackingRequestRepository.findById(requestId);
        if(trackingRequest.isPresent()) {
            trackingRequest.get().setStatus(status);
            trackingRequestRepository.save(trackingRequest.get());
        }
    }

    @Override
    @Transactional
    public void create(TrackingRequestDto trackingRequestDto) {
        trackingRequestRepository.save(trackingRequestConverter.dtoToEntity(trackingRequestDto));
        try {
            this.producer.send(trackingRequestDto);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
