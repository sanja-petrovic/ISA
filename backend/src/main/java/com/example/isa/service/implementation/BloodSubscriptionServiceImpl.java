package com.example.isa.service.implementation;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.example.isa.dto.BloodSubscriptionSignUpDto;
import com.example.isa.dto.SubscriptionResponseDto;
import com.example.isa.kafka.Producer;
import com.example.isa.model.BloodSubscription;
import com.example.isa.repository.BloodSubscriptionRepository;
import com.example.isa.service.interfaces.BloodSubscriptionService;
import com.example.isa.util.converters.SubscriptionSignUpConverter;
import com.fasterxml.jackson.core.JsonProcessingException;

@Service
public class BloodSubscriptionServiceImpl implements BloodSubscriptionService{

	private final BloodSubscriptionRepository repository;
    private final SubscriptionSignUpConverter converter;
    private final Producer producer;
	
	public BloodSubscriptionServiceImpl(BloodSubscriptionRepository repository,SubscriptionSignUpConverter converter,Producer producer) {
		this.repository = repository;
		this.converter = converter;
		this.producer = producer;
	}
	
	@Override
	public List<BloodSubscription> getAll() {
		return repository.findAll();
	}
	public void handleRegistration(BloodSubscriptionSignUpDto dto) throws JsonProcessingException {
		SubscriptionResponseDto responseDto = null;
		if(!this.registerMultiple(converter.Convert(dto))) {
			 responseDto = new SubscriptionResponseDto(dto.SubscriptionId,"SUBSCRIPTION-SUCCESS");
		}
		else {
			 responseDto = new SubscriptionResponseDto(dto.SubscriptionId,"SUBSCRIPTION-SUCCESS-LAST-INVALIDATED");
		}
		System.out.println(responseDto);
		producer.send(responseDto);
	}
	@Override
	public boolean registerMultiple(List<BloodSubscription> subscriptions) {
		boolean retval = false;
		for (BloodSubscription bloodSubscription : subscriptions) {
			BloodSubscription existing= repository.findByBloodBankAndType(bloodSubscription.getBloodBank(), bloodSubscription.getType());
			if ( existing != null){
				repository.delete(existing);
				retval = true;
			}
			repository.save(bloodSubscription);	
		}
		return retval;
	}

	@Override
	public void registerSubscription(BloodSubscription subscription) {
		repository.save(subscription);
	}

	@Override
	public BloodSubscription getById(UUID id) {
		return repository.findById(id).orElse(null);
	}

	@Override
	public List<BloodSubscription> getByBankId(UUID id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BloodSubscription updateBloodSubscription(BloodSubscription bloodSubscription) {
		return repository.save(bloodSubscription);
	}

	@Override
	public List<BloodSubscription> findAllUpcomminActive(String date, int dayOffset) {
		return repository.findAllUpcomminActive(date, dayOffset);
	}

	@Override
	public List<BloodSubscription> findAllTodayActive(String date) {
		return repository.findAllTodayActive(date);
	}
	

}
