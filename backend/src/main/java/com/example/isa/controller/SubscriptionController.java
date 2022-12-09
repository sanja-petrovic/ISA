package com.example.isa.controller;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;

import com.example.isa.model.BloodSubscription;
import com.example.isa.service.interfaces.BloodBankService;
import com.example.isa.service.interfaces.BloodSubscriptionService;

//0 2 * * *
@Controller
public class SubscriptionController {

	private final BloodSubscriptionService service;
	private final BloodBankService bankService;
	
	public SubscriptionController(BloodSubscriptionService service,BloodBankService bankService) {
		this.service = service;
		this.bankService = bankService;
	}
	@Scheduled(cron = "$0 2 * * *")
	public void checkUpcoming() {
		ZoneId defaultZoneId = ZoneId.systemDefault();
		LocalDate firstInMonthDate = LocalDate.of(LocalDate.now().getYear(), LocalDate.now().getMonthValue(), 1);
		List<BloodSubscription> subscriptions = service.findAllUpcomminActive(Date.from(firstInMonthDate.atStartOfDay(defaultZoneId).toInstant()), 3);
		
		if(subscriptions != null) {
			for(BloodSubscription subscription : subscriptions) {
				if(!subscription.getBloodBank().checkBloodSupply(subscription.getType(), subscription.getAmount())) {
					//producer send err
				}
			}
		}
	}
	@Scheduled(cron = "$0 4 * * *")
	public void sendBlood() {
		ZoneId defaultZoneId = ZoneId.systemDefault();
		LocalDate firstInMonthDate = LocalDate.of(LocalDate.now().getYear(), LocalDate.now().getMonthValue(), 1);
		List<BloodSubscription> subscriptions = service.findAllTodayActive(Date.from(firstInMonthDate.atStartOfDay(defaultZoneId).toInstant()));
		
		if(subscriptions != null) {
			for(BloodSubscription subscription : subscriptions) {
				if(subscription.getBloodBank().checkBloodSupply(subscription.getType(), subscription.getAmount())) {
					//producer send ok
				}
				//producer send err
			}
		}
	}
}
