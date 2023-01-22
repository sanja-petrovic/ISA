package com.example.locationsimulator.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TrackingRequest {
    @Id
    private UUID id;
    @Column
    private UUID bloodBankId;
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime timestamp;
    @Embedded
    private Frequency updateFrequency;
    @Embedded
    private Location start;
    @Embedded
    private Location end;
}
