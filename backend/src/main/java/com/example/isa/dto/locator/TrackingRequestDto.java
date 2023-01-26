package com.example.isa.dto.locator;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

public record TrackingRequestDto(UUID id, int frequencyInSeconds, double latitudeStart, double longitudeStart, double latitudeEnd, double longitudeEnd, String status) { }
