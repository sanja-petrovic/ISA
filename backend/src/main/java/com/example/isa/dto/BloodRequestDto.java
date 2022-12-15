package com.example.isa.dto;

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
    private double amount;
    private boolean urgent;
    private String sendOnDate;
}
