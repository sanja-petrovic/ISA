package com.example.isa.util.converters;

import com.example.isa.dto.AdminDto;
import com.example.isa.model.Admin;
import com.example.isa.model.MedicalStaff;
import org.springframework.stereotype.Service;

import java.util.UUID;
@Service
public class AdminConverter implements Converter<Admin,AdminDto> {
    private final EnumConverter enumConverter;
    public AdminConverter(EnumConverter enumConverter) {
        this.enumConverter = enumConverter;
    }
    @Override
    public AdminDto entityToDto(Admin admin) {
       return AdminDto.builder()
                .id(admin.getId().toString())
                .personalId(admin.getPersonalId())
                .email(admin.getEmail())
               .password(admin.getPassword())
               .firstPassword(admin.getFirstPassword())
                .gender(admin.getGender().toString())
                .firstName(admin.getFirstName())
                .lastName(admin.getLastName())
                .phoneNumber(admin.getPhoneNumber())
                .build();
    }

    @Override
    public Admin dtoToEntity(AdminDto adminDto) {
        return Admin.builder()
                .email(adminDto.getEmail())
                .password(adminDto.getPassword())
                .firstPassword(adminDto.getFirstPassword())
                .firstName(adminDto.getFirstName())
                .lastName(adminDto.getLastName())
                .personalId(adminDto.getPersonalId())
                .phoneNumber(adminDto.getPhoneNumber())
                .gender(enumConverter.stringToGender(adminDto.getGender()))
                .build();
    }
}
