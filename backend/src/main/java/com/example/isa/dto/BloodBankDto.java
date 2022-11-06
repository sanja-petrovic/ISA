package com.example.isa.dto;

import com.example.isa.model.Address;
import com.example.isa.model.Interval;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class BloodBankDto {
    private String title;
    private String street;
    private String city;
    private String country;
    private String workingHoursStart;
    private String workingHoursEnd;
    private String description;
    private double averageGrade;
}
