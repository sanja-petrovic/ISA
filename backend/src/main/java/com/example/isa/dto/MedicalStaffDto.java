package com.example.isa.dto;

import com.example.isa.model.Address;
import com.example.isa.model.BloodBank;
import com.example.isa.model.Gender;
import com.example.isa.model.MedicalStaff;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class MedicalStaffDto {
    private String id;
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
    private String bloodBankId;
    private String bloodBankTitle;
    private String bloodBankAddress;
    private String bloodBankAddressNumber;
    private String bloodBankAddressCity;
    private String bloodBankAddressCountry;
    private String bloodBankDescription;

    public MedicalStaff convert() {
        return new MedicalStaff(this.getPersonalId(), this.getFirstName(),this.getLastName(),
                this.getEmail(), this.getPassword(), this.getPhoneNumber(), Gender.valueOf(this.getGender()),
                this.getOccupation(), new Address(this.getHomeAddress(), this.getCity(), this.getCountry()), this.getInstitution(),
                new BloodBank(UUID.fromString(this.getBloodBankId()), this.getBloodBankTitle(),
                        new Address(this.getBloodBankAddressNumber(), this.getBloodBankAddressCity(), this.getBloodBankAddressCountry()),
                        this.getBloodBankDescription()));
    }
}
