package com.example.isa.dto;

import java.util.UUID;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString

public class AppointmentDto {
    private UUID id;
    private String status;
    private String dateTime;
    private Long duration;
    private String bloodBankId;
    private String bloodDonorId;
}
