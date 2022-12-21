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

    private String firstPassword;
    public Admin(String personalId, String email, String password, String firstPassword, String firstName, String lastName, String phoneNumber, Gender gender, boolean verified, Role role) {
        super(personalId, email, password, firstName, lastName, phoneNumber, gender, verified, role);
        this.firstPassword = firstPassword;
    }
    @Builder
    public Admin(String personalId, String email, String password, String firstPassword, String firstName, String lastName, String phoneNumber, Gender gender) {
        super(personalId, email, password, firstName, lastName, phoneNumber, gender, true, null);
        this.firstPassword = firstPassword;

    }
}
