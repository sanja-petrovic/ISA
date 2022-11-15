package com.example.isa.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "medical_staff")
@JsonIgnoreProperties({"bloodBank"})
public class MedicalStaff extends User {
  //  @JsonManagedReference
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private BloodBank bloodBank;

  public MedicalStaff(String personalId, String firstName, String lastName, String email, String password, String phoneNumber, Gender valueOf, String occupation, Address address, String institution, BloodBank byId) {
  }
}
