package com.example.isa.controller;

import com.example.isa.dto.locator.LocationUpdateDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("tracking-requests")
@Api(value = "/tracking-requests", tags = "Location tracking requests")
public class TrackingRequestController {
    @GetMapping("/{requestId}")
    @ApiOperation(value = "Get an update.", httpMethod = "GET")
    public ResponseEntity<LocationUpdateDto> updateLocation(@PathVariable("requestId") String id) {
        return ResponseEntity.ok(new LocationUpdateDto(UUID.fromString(id), 0, 0));
    }
}
