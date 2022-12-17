package com.example.isa.util.converters;

import com.example.isa.dto.BloodBankDto;
import com.example.isa.model.Address;
import com.example.isa.model.BloodBank;
import com.example.isa.model.Interval;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class BloodBankConverter implements Converter<BloodBank, BloodBankDto> {
    @Override
    public BloodBankDto entityToDto(BloodBank bloodBank) {
        return BloodBankDto.builder()
                .id(bloodBank.getId().toString())
                .title(bloodBank.getTitle())
                .street(bloodBank.getAddress().getStreet())
                .city(bloodBank.getAddress().getCity())
                .country(bloodBank.getAddress().getCountry())
                .averageGrade(bloodBank.getAverageGrade())
                .description(bloodBank.getDescription())
                .workingHoursStart(bloodBank.getWorkingHours().getIntervalStart().toString())
                .workingHoursEnd(bloodBank.getWorkingHours().getIntervalEnd().toString())
                .build();
    }

    @Override
    public BloodBank dtoToEntity(BloodBankDto bloodBankDto) {
        SimpleDateFormat format = new SimpleDateFormat("HH:mm");
        try {
            return BloodBank.builder()
                    .title(bloodBankDto.getTitle())
                    .address(new Address(bloodBankDto.getStreet(), bloodBankDto.getCity(), bloodBankDto.getCountry()))
                    .averageGrade(bloodBankDto.getAverageGrade())
                    .description(bloodBankDto.getDescription())
                    .workingHours(new Interval( format.parse(bloodBankDto.getWorkingHoursStart()), format.parse(bloodBankDto.getWorkingHoursEnd())))
                    .build();
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

    }
}
