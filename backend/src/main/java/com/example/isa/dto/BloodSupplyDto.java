package com.example.isa.dto;

import lombok.*;

import java.util.UUID;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BloodSupplyDto {
    private UUID id;
    private String bloodType;
    private double amount;
}
