package com.example.isa.controller;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;

import com.example.isa.dto.SubscriptionResponseDto;
import com.example.isa.kafka.Producer;
import com.example.isa.model.BloodSubscription;
import com.example.isa.service.interfaces.BloodBankService;
import com.example.isa.service.interfaces.BloodSubscriptionService;
import com.fasterxml.jackson.core.JsonProcessingException;

//0 2 * * *
@Controller
public class SubscriptionController {

	private final BloodSubscriptionService service;
	private final BloodBankService bankService;
	private final Producer producer;
	
	public SubscriptionController(BloodSubscriptionService service,BloodBankService bankService,Producer producer) {
		this.service = service;
		this.bankService = bankService;
		this.producer = producer;
	}
	@Scheduled(cron = "1 * * * * *")
	public void checkUpcoming() throws JsonProcessingException {
		System.out.println("Starter check");
		LocalDate firstInMonthDate = LocalDate.of(LocalDate.now().getYear(), LocalDate.now().getMonthValue(), 1);
		List<BloodSubscription> subscriptions = service.findAllUpcomminActive(firstInMonthDate.toString(), 3);
		
		if(subscriptions != null) {
			for(BloodSubscription subscription : subscriptions) {
				if(!subscription.getBloodBank().checkBloodSupply(subscription.getType(), subscription.getAmount())) {
					SubscriptionResponseDto responseDto = new SubscriptionResponseDto(subscription.getOriginId(),"UPCOMING-DELIVERY-FAIL"+ ":"+subscription.getType());
					System.out.println(responseDto);
					producer.send(responseDto);
				}
			}
		}
	}
	@Scheduled(cron = "1 * * * * *")
	public void sendBlood() throws JsonProcessingException {
		System.out.println("Started sending");
		LocalDate firstInMonthDate = LocalDate.of(LocalDate.now().getYear(), LocalDate.now().getMonthValue(), 1);
		List<BloodSubscription> subscriptions = service.findAllTodayActive(firstInMonthDate.toString());
		
		if(subscriptions == null) return;
		for(BloodSubscription subscription : subscriptions) {
			if(subscription.getBloodBank().checkBloodSupply(subscription.getType(), subscription.getAmount())) {
				if(bankService.checkBloodSupply(subscription.getBloodBank(), subscription.getType(), subscription.getAmount())) {
					SubscriptionResponseDto responseDto = new SubscriptionResponseDto(subscription.getOriginId(),"DELIVERY-SUCCESS" + ":"+subscription.getType());
					System.out.println(responseDto);
					producer.send(responseDto);
				}
			}
			SubscriptionResponseDto responseDto = new SubscriptionResponseDto(subscription.getOriginId(),"DELIVERY-FAIL"+ ":"+subscription.getType());
			producer.send(responseDto);
		}
	}
}
