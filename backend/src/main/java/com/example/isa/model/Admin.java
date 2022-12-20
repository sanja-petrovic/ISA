package com.example.isa.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Table;

@Table(name = "admins")
@Getter
@Setter
@Entity
public class Admin extends User {
    public Admin(String personalId, String email, String password, String firstName, String lastName, String phoneNumber, Gender gender, boolean verified, Role roles) {
        super(personalId, email, password, firstName, lastName, phoneNumber, gender, verified, roles);
    }

    public Admin() {
        super();
    }
}
