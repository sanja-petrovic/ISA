package com.example.isa.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

import com.example.isa.util.converters.DateConverter;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@ToString
@Table(name = "appointments")
public class Appointment extends BaseEntity {

    @Column
    @Enumerated(EnumType.STRING)
    private AppointmentStatus status;
    @Column
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateTime;
    @Column
    private Long duration;
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn
    private BloodBank bloodBank;
    @ManyToOne(optional = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "blood_donor_id")
    private BloodDonor bloodDonor;
    @Column(columnDefinition="TEXT")
    private String report;

    
    public Appointment(Appointment appointment) {
        status = AppointmentStatus.NOT_SCHEDULED;
        dateTime = appointment.dateTime;
        duration = appointment.duration;
        bloodBank = appointment.bloodBank;
    }
    
}
