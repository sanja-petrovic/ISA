package com.example.isa.util.converters;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.messaging.handler.invocation.ReactiveReturnValueHandler;
import org.springframework.stereotype.Component;

import com.example.isa.dto.BloodProductDto;
import com.example.isa.dto.BloodSubscriptionSignUpDto;
import com.example.isa.dto.BloodTypeDto;
import com.example.isa.model.BloodBank;
import com.example.isa.model.BloodSubscription;
import com.example.isa.model.BloodType;
import com.example.isa.service.interfaces.BloodBankService;
import com.example.isa.service.interfaces.BloodSubscriptionService;

import net.bytebuddy.asm.Advice.This;
import org.springframework.stereotype.Service;

@Service
public class SubscriptionSignUpConverter {
	private final BloodBankService service;
	
	public SubscriptionSignUpConverter(BloodBankService service) {
		this.service = service;
	}
	public List<BloodSubscription> Convert(BloodSubscriptionSignUpDto dto){
		List<BloodSubscription> retVal = new ArrayList<>();
		BloodBank bank = service.findByTitle(dto.BloodBank);
		for(BloodProductDto type : dto.Blood){
			
			retVal.add(new BloodSubscription(BloodType.valueOf(type.BloodType.BloodGroup+"_"+type.BloodType.RhFactor),type.Amount,dto.DeliveryDay,true,dto.SubscriptionId,bank));
		}
		return retVal;
	}
}
