package com.example.isa.util.converters;

import com.example.isa.dto.SuppliesDto;
import com.example.isa.model.Supplies;
import org.springframework.stereotype.Service;

@Service
public class SuppliesConverter implements Converter<Supplies, SuppliesDto> {


    @Override
    public SuppliesDto entityToDto(Supplies supplies) {
        return SuppliesDto.builder()
                .id(supplies.getId())
                .amount(supplies.getAmount())
                .name(supplies.getName())
                .build();
    }

    @Override
    public Supplies dtoToEntity(SuppliesDto suppliesDto) {
        return Supplies.builder()
                .amount(suppliesDto.getAmount())
                .name(suppliesDto.getName())
                .build();
    }
}
