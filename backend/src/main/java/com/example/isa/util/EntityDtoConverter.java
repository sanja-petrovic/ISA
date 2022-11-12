package com.example.isa.util;

import com.example.isa.dto.PatientDto;
import com.example.isa.model.Address;
import com.example.isa.model.Patient;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class EntityDtoConverter {
    private final EnumConverter enumConverter;
    private final PasswordEncoder passwordEncoder;

    public EntityDtoConverter(EnumConverter enumConverter, PasswordEncoder passwordEncoder) {
        this.enumConverter = enumConverter;
        this.passwordEncoder = passwordEncoder;
    }

    public PatientDto PatientToDto(Patient patient) {
        PatientDto dto = PatientDto.builder()
                .personalId(patient.getPersonalId())
                .firstName(patient.getFirstName())
                .lastName(patient.getLastName())
                .email(patient.getEmail())
                .phoneNumber(patient.getPhoneNumber())
                .gender(patient.getGender().toString())
                .occupation(patient.getOccupation())
                .homeAddress(patient.getAddress().getStreet())
                .city(patient.getAddress().getCity())
                .country(patient.getAddress().getCountry())
                .institution(patient.getInstitution())
                .build();

        return dto;
    }

    public Patient DtoToPatient(PatientDto dto) {
        Patient patient = Patient.builder()
                .personalId(dto.getPersonalId())
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .email(dto.getEmail())
                .password(passwordEncoder.encode(dto.getPassword()))
                .phoneNumber(dto.getPhoneNumber())
                .gender(enumConverter.StringToGender(dto.getGender()))
                .occupation(dto.getOccupation())
                .address(new Address(dto.getHomeAddress(), dto.getCity(), dto.getCountry()))
                .institution(dto.getInstitution())
                .build();

        return patient;
    }
}
