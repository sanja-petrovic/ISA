package com.example.isa.service.implementation;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.example.isa.dto.BloodSubscriptionSignUpDto;
import com.example.isa.dto.SubscriptionSignUpResponceDto;
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
		this.registerMultiple(converter.Convert(dto));
		SubscriptionSignUpResponceDto responceDto = new SubscriptionSignUpResponceDto(dto.SubscriptionId,"SUBSCRIPTION-SUCCESS");
		producer.send(responceDto);
	}
	@Override
	public void registerMultiple(List<BloodSubscription> subscriptions) {
		for (BloodSubscription bloodSubscription : subscriptions) {
			repository.save(bloodSubscription);	
		}
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
	

}
