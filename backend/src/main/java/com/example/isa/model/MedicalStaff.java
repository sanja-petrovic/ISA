package com.example.isa.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
import java.util.List;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "medical_staff")
@JsonIgnoreProperties({"bloodBank"})
public class MedicalStaff extends User {
    //  @JsonManagedReference
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private BloodBank bloodBank;

    public MedicalStaff(String personalId, String email, String password, String firstName, String lastName, String phoneNumber, Gender gender, boolean verified, List<Role> roles, BloodBank bloodBank) {
        super(personalId, email, password, firstName, lastName, phoneNumber, gender, verified, roles);
        this.bloodBank = bloodBank;
    }

    @Builder
    public MedicalStaff(String personalId, String email, String password, String firstName, String lastName, String phoneNumber, Gender gender, BloodBank bloodBank) {
        super(personalId, email, password, firstName, lastName, phoneNumber, gender);
        this.bloodBank = bloodBank;
    }
}
