package com.example.locationsimulator.model;

import lombok.*;

import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TrackingRequest {
    private UUID id;
    private Date timestamp;
    private int frequencyInSeconds;
    private Location start;
    private Location end;
    private TrackingRequestStatus status;
}
