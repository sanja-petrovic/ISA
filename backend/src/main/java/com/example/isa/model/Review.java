package com.example.isa.model;

import javax.persistence.*;
import java.util.UUID;

@Table(name = "reviews")
@Entity
public class Review {
    @Id
    private UUID id;
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn
    private Patient reviewer;
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn
    private BloodBank bloodBank;
    private String content;
}
