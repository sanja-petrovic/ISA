package com.example.locationsimulator.dto;

import java.util.UUID;

public record ResponseDto(UUID requestId, double latitude, double longitude) {
}
