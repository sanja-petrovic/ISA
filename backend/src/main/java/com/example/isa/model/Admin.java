package com.example.isa.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "admins")
public class Admin extends User {
    public Admin(String personalId, String email, String password, String firstName, String lastName, String phoneNumber, Gender gender, boolean verified, List<Role> roles) {
        super(personalId, email, password, firstName, lastName, phoneNumber, gender, verified, roles);
    }
    @Builder
    public Admin(String personalId, String email, String password, String firstName, String lastName, String phoneNumber, Gender gender) {
        super(personalId, email, password, firstName, lastName, phoneNumber, gender, true, null);
    }
}
