package com.example.isa.dto;

import com.example.isa.model.Admin;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class AdminDto {
    private String id;
    private String personalId;
    private String email;

    private String password;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String gender;
    private boolean isVerified;

    public AdminDto(Admin admin){
        this.id = admin.getId().toString();
        this.personalId = admin.getPersonalId();
        this.email = admin.getEmail();
        this.password = admin.getPassword();
        this.firstName = admin.getFirstName();
        this.lastName = admin.getLastName();
        this.phoneNumber = admin.getPhoneNumber();
        this.gender = admin.getGender().toString();
        this.isVerified = admin.isVerified();
    }
}
