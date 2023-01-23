package com.example.isa.dto.locator;

import java.util.UUID;

public record LocationUpdateDto(UUID requestId, double latitude, double longitude) {
}
