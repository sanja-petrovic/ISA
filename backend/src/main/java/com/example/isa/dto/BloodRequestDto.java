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
    private UUID Id;
    private String Bank;
    private String BloodType;
    private String RhFactor;
    private double Amount;
    private boolean Urgent;
    private Date SendOnDate;
}
