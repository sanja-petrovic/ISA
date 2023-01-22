package com.example.locationsimulator.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(value = "tracking_requests")
public class TrackingRequest {
    @PrimaryKey
    private UUID id;
    @JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ss")
    private Date timestamp;
    private Frequency updateFrequency;
    private Location start;
    private Location end;
}
