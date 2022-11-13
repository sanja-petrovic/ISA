package com.example.isa.dto;

import com.example.isa.model.Address;
import com.example.isa.model.BloodBank;
import com.example.isa.model.Interval;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.beans.ConstructorProperties;

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

}
