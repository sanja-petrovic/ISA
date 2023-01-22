package com.example.locationsimulator.model;

import jakarta.persistence.Embeddable;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class Frequency {
    private double value;
    private TimeUnit unit;
}
