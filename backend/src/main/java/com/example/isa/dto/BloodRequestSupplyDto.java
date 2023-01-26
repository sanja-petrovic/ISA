package com.example.isa.dto;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BloodRequestSupplyDto {
    private UUID requestId;
    private String bloodType;
    private double amount;
}

