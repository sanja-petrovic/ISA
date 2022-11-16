package com.example.isa.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "appointments")
public class Appointment {
    @Id
    @Column
    private UUID uuid;
    @Column
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateTime;
    @Column
    private Long duration;
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn
    private BloodBank bloodBank;
}
