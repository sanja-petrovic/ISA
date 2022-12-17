package com.example.isa.dto;

import com.example.isa.model.BloodBank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BloodRequestDto {
    private UUID id;
    private String bloodType;
    private String bloodBank;
    private double amount;
    private boolean urgent;
    private String sendOnDate;

    public BloodRequestDto(UUID id, String bloodType, double amount, boolean urgent, String sendOnDate) {
        this.id = id;
        this.bloodType = bloodType;
        this.amount = amount;
        this.urgent = urgent;
        this.sendOnDate = sendOnDate;
    }
}
