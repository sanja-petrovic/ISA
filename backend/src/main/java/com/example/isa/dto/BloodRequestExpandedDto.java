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
public class BloodRequestExpandedDto {
    private UUID id;
    private String bloodType;
    private double amount;
    private boolean urgent;
    private BloodBankRequestDto bloodBank;
    private String sendOnDate;
}
