package com.example.isa.service.interfaces;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Sort;

import com.example.isa.dto.BloodSubscriptionSignUpDto;
import com.example.isa.model.BloodSubscription;
import com.fasterxml.jackson.core.JsonProcessingException;


public interface BloodSubscriptionService {
	public List<BloodSubscription> getAll();
	public void registerMultiple (List<BloodSubscription> subscriptions); 
    public void registerSubscription (BloodSubscription subscription);
    public BloodSubscription getById(UUID id);
    public List<BloodSubscription> getByBankId(UUID id);
    public BloodSubscription updateBloodSubscription(BloodSubscription bloodSubscription);
    public void handleRegistration(BloodSubscriptionSignUpDto dto) throws JsonProcessingException;
    public List<BloodSubscription>findAllUpcomminActive(Date firstInMonth, int dayOffset);
    public List<BloodSubscription>findAllTodayActive(Date firstInMonth);
}
