package com.example.isa.dto;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NewsDto {
    private UUID id;
    private String bloodBank;
    private String title;
    private String body;
    private long milliseconds;
}
