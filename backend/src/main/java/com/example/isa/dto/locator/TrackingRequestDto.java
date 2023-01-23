package com.example.isa.dto.locator;

import java.util.UUID;

public record TrackingRequestDto(UUID id, int frequencyTimeValue, double frequencyTimeUnit, double latitudeStart, double longitudeStart, double latitudeEnd, double longitudeEnd, String status) { }
