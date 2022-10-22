package com.example.isa.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "blood_banks")
public class BloodBank {
    @Id
    private UUID id;
    @Column
    private String title;
    @Embedded
    private Address address;
    @Column
    private String description;
    @Column
    private double averageGrade;
    @JsonBackReference
    @OneToMany(mappedBy = "bloodBank", cascade = CascadeType.ALL)
    private Set<MedicalStaff> medicalStaff;


    public BloodBank(String title, Address address, String description) {
        this.id = UUID.randomUUID();
        this.title = title;
        this.address = address;
        this.description = description;
    }
}
