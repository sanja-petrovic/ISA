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
                .institutionInfo(dto.getInstitution())
                .build();

        return patient;
    }
}
