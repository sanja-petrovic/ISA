package com.example.isa.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BloodBankSearchSortDto {
    List<String> searchCriteria;
    SortDto sortCriteria;
    String filterGrade;
}
