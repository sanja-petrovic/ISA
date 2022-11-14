package com.example.isa.dto;

import com.example.isa.model.Address;
import com.example.isa.model.BloodBank;
import com.example.isa.model.Interval;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.beans.ConstructorProperties;
import java.rmi.server.UID;
import java.util.UUID;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class BloodBankDto {
    private String id;
    private String title;
    private String street;
    private String city;
    private String country;
    private String workingHoursStart;
    private String workingHoursEnd;
    private String description;
    private double averageGrade;

    public BloodBankDto(String title, String street, String city, String country, String workingHoursStart, String workingHoursEnd, String description, double averageGrade){
        this.title = title;
        this.street = street;
        this.city = city;
        this.country = country;
        this.workingHoursStart = workingHoursStart;
        this.workingHoursEnd = workingHoursEnd;
        this.description = description;
        this.averageGrade = averageGrade;
    }

    public BloodBankDto(BloodBank bloodBank){
        this.id = bloodBank.getId().toString();
        this.title = bloodBank.getTitle();
        this.street = bloodBank.getAddress().getStreet();
        this.city = bloodBank.getAddress().getCity();
        this.country = bloodBank.getAddress().getCountry();
        this.workingHoursStart = bloodBank.getWorkingHours().getIntervalStart().toString();
        this.workingHoursEnd = bloodBank.getWorkingHours().getIntervalEnd().toString();
        this.description = bloodBank.getDescription();
        this.averageGrade = bloodBank.getAverageGrade();
    }

}
