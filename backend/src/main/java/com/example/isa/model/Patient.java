package com.example.isa.model;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Table(name = "patients")
@Entity
public class Patient extends User {
    @Column
    private String occupation;
    @Embedded
    private Address address;
    @Column
    private String institution;
    @Builder
    public Patient(String personalId, String email, String password, String firstName, String lastName, String phoneNumber, Gender gender, boolean verified, String occupation, Address address, String institution, List<Role> roles) {
        super(personalId, email, password, firstName, lastName, phoneNumber, gender, verified, roles);
        this.occupation = occupation;
        this.address = address;
        this.institution = institution;
    }
}
