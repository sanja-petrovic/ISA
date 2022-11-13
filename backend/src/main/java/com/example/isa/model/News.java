package com.example.isa.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "news")
public class News {
    @Id
    private UUID id;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "blood_bank_id")
    private BloodBank bloodBank;
    @Column
    private String title;
    @Column
    private String body;
    @Column
    @Temporal(TemporalType.DATE)
    private Date timestamp;
}
