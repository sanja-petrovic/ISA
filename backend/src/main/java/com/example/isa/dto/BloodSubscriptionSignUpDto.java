package com.example.isa.dto;

import java.util.List;

import com.example.isa.model.BloodType;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class BloodSubscriptionSignUpDto {
	public List<BloodProductDto> Blood;
    public String BloodBank;
    public String SubscriptionId;
    
    public int DeliveryDay;
}
