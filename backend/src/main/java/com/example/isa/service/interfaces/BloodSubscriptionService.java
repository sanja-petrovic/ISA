package com.example.isa.service.interfaces;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Sort;

import com.example.isa.dto.BloodSubscriptionSignUpDto;
import com.example.isa.model.BloodSubscription;
import com.fasterxml.jackson.core.JsonProcessingException;


public interface BloodSubscriptionService {
	List<BloodSubscription> getAll();
	void registerMultiple (List<BloodSubscription> subscriptions);
    void registerSubscription (BloodSubscription subscription);
    BloodSubscription getById(UUID id);
    List<BloodSubscription> getByBankId(UUID id);
    BloodSubscription updateBloodSubscription(BloodSubscription bloodSubscription);
    void handleRegistration(BloodSubscriptionSignUpDto dto) throws JsonProcessingException;
    List<BloodSubscription>findAllUpcomminActive(String date, int dayOffset);
    List<BloodSubscription>findAllTodayActive(String date);
}
