package com.example.isa.dto;

import java.util.UUID;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class AppointmentDto {
    private UUID uuid;
    private String dateTime;
    private Long duration;
    private String bloodBankId;
    private String bloodDonorId;
}
