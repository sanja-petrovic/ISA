package com.example.isa.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Table(name = "blood_donors")
@Entity
public class BloodDonor extends User {
    @Column
    private String occupation;
    @Embedded
    private Address address;
    @Column
    private String institution;
    @Column
    private int penalty;
    @Column
    @Enumerated(EnumType.STRING)
    private LoyaltyStatus loyaltyStatus;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "blood_donor_id")
    private List<Answer> answers = new ArrayList<>();

    @Builder
    public BloodDonor(String personalId, String email, String password, String firstName, String lastName, String phoneNumber, Gender gender, boolean verified, String occupation, Address address, String institution, Role roles, int penalty) {
        super(personalId, email, password, firstName, lastName, phoneNumber, gender, verified, roles);
        this.occupation = occupation;
        this.address = address;
        this.institution = institution;
        this.penalty = penalty;
    }
}
