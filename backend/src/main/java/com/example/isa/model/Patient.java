package com.example.isa.model;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Table(name = "users")
@Entity
public class Patient extends Account {
    @Column
    private String occupation;
    @Embedded
    private Address address;
    @Column
    private String institutionInfo;
    @Builder
    public Patient(String personalId, String email, String password, String firstName, String lastName, String phoneNumber, Gender gender, String occupation, Address address, String institutionInfo) {
        super(personalId, email, password, firstName, lastName, phoneNumber, gender);
        this.occupation = occupation;
        this.address = address;
        this.institutionInfo = institutionInfo;
    }
}
