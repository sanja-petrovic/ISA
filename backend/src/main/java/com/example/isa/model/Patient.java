package com.example.isa.model;

import lombok.*;

import javax.persistence.*;

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
    private String institutionInfo;
    @Builder
    public Patient(String personalId, AccountStatus status, String email, String password, String firstName, String lastName, String phoneNumber, Gender gender, boolean verified, String occupation, Address address, String institutionInfo) {
        super(personalId, status, email, password, firstName, lastName, phoneNumber, gender, verified);
        this.occupation = occupation;
        this.address = address;
        this.institutionInfo = institutionInfo;
    }
}
