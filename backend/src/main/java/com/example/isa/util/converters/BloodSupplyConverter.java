package com.example.isa.util.converters;

import com.example.isa.dto.BloodSupplyDto;
import com.example.isa.model.BloodSupply;
import com.example.isa.model.BloodType;
import org.springframework.stereotype.Service;

@Service
public class BloodSupplyConverter implements Converter<BloodSupply, BloodSupplyDto> {
    @Override
    public BloodSupplyDto entityToDto(BloodSupply bloodSupply) {
        return BloodSupplyDto.builder()
                .id(bloodSupply.getId())
                .amount(bloodSupply.getAmount())
                .bloodType(bloodSupply.getType().toString())
                .build();
    }

    @Override
    public BloodSupply dtoToEntity(BloodSupplyDto bloodSupplyDto) {
        return BloodSupply.builder()
                .amount(bloodSupplyDto.getAmount())
                .id(bloodSupplyDto.getId())
                .type(BloodType.valueOf(bloodSupplyDto.getBloodType()))
                .build();
    }
}
