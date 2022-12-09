package com.example.isa.controller;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;

import com.example.isa.dto.SubscriptionResponceDto;
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
	@Scheduled(cron = "$0 2 * * *")
	public void checkUpcoming() throws JsonProcessingException {
		ZoneId defaultZoneId = ZoneId.systemDefault();
		LocalDate firstInMonthDate = LocalDate.of(LocalDate.now().getYear(), LocalDate.now().getMonthValue(), 1);
		List<BloodSubscription> subscriptions = service.findAllUpcomminActive(Date.from(firstInMonthDate.atStartOfDay(defaultZoneId).toInstant()), 3);
		
		if(subscriptions != null) {
			for(BloodSubscription subscription : subscriptions) {
				if(!subscription.getBloodBank().checkBloodSupply(subscription.getType(), subscription.getAmount())) {
					SubscriptionResponceDto responceDto = new SubscriptionResponceDto(subscription.getOriginId(),"UPCOMING-DELIVERY-FAIL");
					producer.send(responceDto);
				}
			}
		}
	}
	@Scheduled(cron = "$0 4 * * *")
	public void sendBlood() throws JsonProcessingException {
		ZoneId defaultZoneId = ZoneId.systemDefault();
		LocalDate firstInMonthDate = LocalDate.of(LocalDate.now().getYear(), LocalDate.now().getMonthValue(), 1);
		List<BloodSubscription> subscriptions = service.findAllTodayActive(Date.from(firstInMonthDate.atStartOfDay(defaultZoneId).toInstant()));
		
		if(subscriptions == null) return;
		for(BloodSubscription subscription : subscriptions) {
			if(subscription.getBloodBank().checkBloodSupply(subscription.getType(), subscription.getAmount())) {
				if(bankService.updateBloodSupplies(subscription.getBloodBank(), subscription.getType(), subscription.getAmount())) {
					SubscriptionResponceDto responceDto = new SubscriptionResponceDto(subscription.getOriginId(),"DELIVERY-SUCCESS");
					producer.send(responceDto);
				}
			}
			SubscriptionResponceDto responceDto = new SubscriptionResponceDto(subscription.getOriginId(),"DELIVERY-FAIL");
			producer.send(responceDto);
		}
	}
}
