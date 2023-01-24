package com.example.isa.controller;

import com.example.isa.dto.locator.LocationUpdateDto;
import com.example.isa.dto.locator.TrackingRequestDto;
import com.example.isa.model.locator.Frequency;
import com.example.isa.model.locator.Location;
import com.example.isa.model.locator.TrackingRequest;
import com.example.isa.model.locator.TrackingRequestStatus;
import com.example.isa.service.interfaces.TrackingRequestService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("tracking-requests")
@Api(value = "/tracking-requests", tags = "Location tracking requests")
public class TrackingRequestController {
    private final TrackingRequestService trackingRequestService;

    public TrackingRequestController(TrackingRequestService trackingRequestService) {
        this.trackingRequestService = trackingRequestService;
    }

    @PostMapping
    @ApiOperation(value = "Start the session.", httpMethod = "POST")
    public ResponseEntity start() {
        TrackingRequestDto trackingRequestDto = new TrackingRequestDto(UUID.randomUUID(), 5, TimeUnit.SECONDS, 0.0, 0.0, 0.0, 0.0, TrackingRequestStatus.RECEIVED.toString());
        TrackingRequest trackingRequest = new TrackingRequest(UUID.randomUUID(), new Date(), null, new Frequency(5, TimeUnit.SECONDS), new Location(0.0, 0.0), new Location(0.0, 0.0), TrackingRequestStatus.RECEIVED);
        trackingRequestService.create(trackingRequest);
        return ResponseEntity.ok().build();
    }
}
