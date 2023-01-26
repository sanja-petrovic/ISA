package com.example.locationsimulator.dto;

import java.util.UUID;

public record LocationUpdateDto(UUID requestId, double latitude, double longitude) {
}
