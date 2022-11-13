package com.example.isa.util.Converters;

import com.example.isa.dto.PatientDto;
import com.example.isa.model.Address;
import com.example.isa.model.Patient;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class PatientConverter implements Converter<Patient, PatientDto> {

    private final EnumConverter enumConverter;
    private final PasswordEncoder passwordEncoder;

    public PatientConverter(EnumConverter enumConverter, PasswordEncoder passwordEncoder) {
        this.enumConverter = enumConverter;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public PatientDto entityToDto(Patient patient) {
        return PatientDto.builder()
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
    }

    @Override
    public Patient dtoToEntity(PatientDto patientDto) {
        return Patient.builder()
                .personalId(patientDto.getPersonalId())
                .firstName(patientDto.getFirstName())
                .lastName(patientDto.getLastName())
                .email(patientDto.getEmail())
                .password(passwordEncoder.encode(patientDto.getPassword()))
                .phoneNumber(patientDto.getPhoneNumber())
                .gender(enumConverter.stringToGender(patientDto.getGender()))
                .occupation(patientDto.getOccupation())
                .address(new Address(patientDto.getHomeAddress(), patientDto.getCity(), patientDto.getCountry()))
                .institution(patientDto.getInstitution())
                .build();
    }
}
