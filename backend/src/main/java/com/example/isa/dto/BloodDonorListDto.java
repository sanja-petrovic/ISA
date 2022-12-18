package com.example.isa.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class BloodDonorListDto {
    private List<BloodDonorDto> userDtos;
}
