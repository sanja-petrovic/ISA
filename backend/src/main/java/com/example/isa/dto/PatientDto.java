package com.example.isa.dto;

import com.example.isa.model.Address;
import com.example.isa.model.Gender;
import com.example.isa.model.Patient;

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

    public PatientDto(Patient patient) {
    	this.personalId = patient.getPersonalId();
    	this.firstName = patient.getFirstName();
    	this.lastName = patient.getLastName();
    	this.email = patient.getEmail();
    	this.password = patient.getPassword();
    	this.phoneNumber = patient.getPhoneNumber();
    	this.gender = patient.getGender().toString();
    	this.occupation = patient.getOccupation();
    	this.homeAddress = patient.getAddress().getStreet();
    	this.number = "8";
    	this.city = patient.getAddress().getCountry();
    	this.country = patient.getAddress().getCountry();
    	this.institution = patient.getInstitutionInfo();
    }
    public Patient convert() {
    	Patient retVal = new Patient();
    	retVal.setPersonalId(this.getPersonalId());
    	retVal.setFirstName(this.getFirstName());
    	retVal.setLastName(this.getLastName());
    	retVal.setEmail(this.getEmail());
    	retVal.setPassword(this.getPassword());
    	retVal.setPhoneNumber(this.getPhoneNumber());
    	retVal.setGender(Gender.valueOf(this.getGender()));
    	retVal.setOccupation(this.getOccupation());
    	retVal.setAddress(new Address(this.getHomeAddress(), this.getCity(), this.getCountry()));
    	retVal.setInstitutionInfo(this.getInstitution());
    	return retVal;
    }
}

