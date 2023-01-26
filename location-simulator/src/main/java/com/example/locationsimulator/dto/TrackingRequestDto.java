package com.example.locationsimulator.dto;

import java.util.UUID;

public record TrackingRequestDto(UUID id, int frequencyInSeconds, double latitudeStart, double longitudeStart, double latitudeEnd, double longitudeEnd) { }
