package com.example.isa.dto;

import lombok.Builder;

import com.example.isa.model.Address;
import com.example.isa.model.Gender;
import com.example.isa.model.Patient;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
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
    private String city;
    private String country;
    private String institution;
}

