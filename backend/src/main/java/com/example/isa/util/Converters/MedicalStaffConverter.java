package com.example.isa.util.Converters;

import com.example.isa.dto.MedicalStaffDto;
import com.example.isa.model.MedicalStaff;
import com.example.isa.service.interfaces.BloodBankService;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class MedicalStaffConverter implements Converter<MedicalStaff, MedicalStaffDto> {
    private final BloodBankService bloodBankService;
    private final EnumConverter enumConverter;

    public MedicalStaffConverter(BloodBankService bloodBankService, EnumConverter enumConverter) {
        this.bloodBankService = bloodBankService;
        this.enumConverter = enumConverter;
    }

    @Override
    public MedicalStaffDto entityToDto(MedicalStaff medicalStaff) {
        return MedicalStaffDto.builder()
                .id(medicalStaff.getId().toString())
                .personalId(medicalStaff.getPersonalId())
                .email(medicalStaff.getEmail())
                .gender(medicalStaff.getGender().toString())
                .firstName(medicalStaff.getFirstName())
                .lastName(medicalStaff.getLastName())
                .phoneNumber(medicalStaff.getPhoneNumber())
                .bloodBankId(medicalStaff.getBloodBank().getId().toString())
                .build();
    }

    @Override
    public MedicalStaff dtoToEntity(MedicalStaffDto medicalStaffDto) {
        return MedicalStaff.builder()
                .bloodBank(bloodBankService.getById(UUID.fromString(medicalStaffDto.getBloodBankId())))
                .firstName(medicalStaffDto.getFirstName())
                .lastName(medicalStaffDto.getLastName())
                .personalId(medicalStaffDto.getPersonalId())
                .phoneNumber(medicalStaffDto.getPhoneNumber())
                .gender(enumConverter.stringToGender(medicalStaffDto.getGender()))
                .email(medicalStaffDto.getEmail())
                .build();
    }
}
