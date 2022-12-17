package com.example.isa.dto;

import lombok.*;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class BloodRequestResponseDto {
    private UUID requestId;
    private String status;
}
