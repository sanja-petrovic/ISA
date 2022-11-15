package com.example.isa.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@Entity
@AllArgsConstructor
@Table(name = "blood_banks")
public class BloodBank {
    @Id
    private UUID id;
    @Column
    private String title;
    @Embedded
    private Address address;
    @Embedded
    private Interval workingHours;
    @Column
    private String description;
    @Column
    private double averageGrade;
    @JsonBackReference
    @OneToMany(mappedBy = "bloodBank", cascade = CascadeType.ALL)
    private Set<MedicalStaff> medicalStaff;

    @JsonBackReference
    @OneToMany(mappedBy = "bloodBank", cascade = CascadeType.ALL)
    private Set<BloodSupply> bloodSupplies;

    public BloodBank(String title, Address address, String description) {
        this.id = UUID.randomUUID();
        this.title = title;
        this.address = address;
        this.description = description;
    }

    public BloodBank(UUID id, String title, Address address, String description) {
        this.id = UUID.randomUUID();
        this.title = title;
        this.address = address;
        this.description = description;
    }

    @Builder
    public BloodBank(String title, Address address, Interval workingHours, String description, double averageGrade) {
        this.title = title;
        this.address = address;
        this.workingHours = workingHours;
        this.description = description;
        this.averageGrade = averageGrade;
    }
}
