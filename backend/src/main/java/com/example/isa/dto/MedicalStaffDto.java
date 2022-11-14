package com.example.isa.dto;

import com.example.isa.model.Address;
import com.example.isa.model.BloodBank;
import com.example.isa.model.Gender;
import com.example.isa.model.MedicalStaff;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class MedicalStaffDto {
    private String id;
    private String personalId;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String phoneNumber;
    private String gender;
    private String bloodBankId;

}
