package com.example.isa.util.converters;

import com.example.isa.dto.BloodDonorDto;
import com.example.isa.model.Address;
import com.example.isa.model.BloodDonor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class BloodDonorConverter implements Converter<BloodDonor, BloodDonorDto> {

    private final EnumConverter enumConverter;
    private final PasswordEncoder passwordEncoder;

    public BloodDonorConverter(EnumConverter enumConverter, PasswordEncoder passwordEncoder) {
        this.enumConverter = enumConverter;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public BloodDonorDto entityToDto(BloodDonor bloodDonor) {
        return BloodDonorDto.builder()
                .personalId(bloodDonor.getPersonalId())
                .firstName(bloodDonor.getFirstName())
                .lastName(bloodDonor.getLastName())
                .email(bloodDonor.getEmail())
                .phoneNumber(bloodDonor.getPhoneNumber())
                .password(bloodDonor.getPassword())
                .gender(bloodDonor.getGender().toString())
                .occupation(bloodDonor.getOccupation())
                .homeAddress(bloodDonor.getAddress().getStreet())
                .city(bloodDonor.getAddress().getCity())
                .country(bloodDonor.getAddress().getCountry())
                .institution(bloodDonor.getInstitution())
                .penaltyCount(bloodDonor.getPenaltyCount())
                .build();
    }

    @Override
    public BloodDonor dtoToEntity(BloodDonorDto bloodDonorDto) {
        return BloodDonor.builder()
                .personalId(bloodDonorDto.getPersonalId())
                .firstName(bloodDonorDto.getFirstName())
                .lastName(bloodDonorDto.getLastName())
                .email(bloodDonorDto.getEmail())
                .password(passwordEncoder.encode(bloodDonorDto.getPassword()))
                .phoneNumber(bloodDonorDto.getPhoneNumber())
                .gender(enumConverter.stringToGender(bloodDonorDto.getGender()))
                .occupation(bloodDonorDto.getOccupation())
                .address(new Address(bloodDonorDto.getHomeAddress(), bloodDonorDto.getCity(), bloodDonorDto.getCountry()))
                .institution(bloodDonorDto.getInstitution())
                .penalty(bloodDonorDto.getPenaltyCount())
                .build();
    }
}
