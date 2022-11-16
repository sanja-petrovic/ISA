package com.example.isa.model;

import javax.persistence.*;
import java.util.*;

@Table(name = "reports")
@Entity
public class Report {
    @Id
    private UUID id;
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn
    private Appointment appointment;
    @Column
    private boolean approved;
    @Column
    private boolean penalty;
    @Column
    private String information;


}
