package com.example.isa.dto;

import java.util.List;

import javax.persistence.Column;

import com.example.isa.model.BloodType;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class BloodTypeDto {
	public String BloodGroup;
    public String RhFactor;
}
