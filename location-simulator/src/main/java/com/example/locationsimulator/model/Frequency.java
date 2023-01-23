package com.example.locationsimulator.model;

import lombok.*;

import java.util.concurrent.TimeUnit;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Frequency {
    private int value;
    private TimeUnit unit;
}
