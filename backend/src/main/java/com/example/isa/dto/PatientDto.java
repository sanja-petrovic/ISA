package com.example.isa.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class PatientDto {
    private String personalId;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String phoneNumber;
    private String gender;
    private String occupation;
    private String homeAddress;
    private String number;
    private String city;
    private String country;
    private String institution;

}
