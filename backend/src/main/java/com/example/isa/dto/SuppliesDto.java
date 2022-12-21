package com.example.isa.dto;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SuppliesDto {
    private UUID id;
    private int amount;
    private String name;
}
