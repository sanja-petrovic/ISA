package com.example.isa.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "blood_requests")
public class BloodRequest {
    @Id
    private UUID id;
    @ManyToOne
    @JoinColumn(name = "blood_bank_id")
    private BloodBank bloodBank;
    @Column
    private BloodType bloodType;
    @Column
    private Double amount;
    @Column
    @Enumerated(EnumType.STRING)
    private BloodRequestStatus status;
    @Column
    @Temporal(TemporalType.TIMESTAMP)
    private Date receivedDate;
    @Column
    @Temporal(TemporalType.TIMESTAMP)
    private Date sendOnDate;
}
