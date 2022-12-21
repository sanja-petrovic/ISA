package com.example.isa.service.implementation;

import com.example.isa.exception.NotFoundException;
import com.example.isa.model.*;
import com.example.isa.repository.BloodDonorRepository;
import com.example.isa.service.interfaces.BloodDonorService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.relational.core.sql.TrueCondition;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BloodDonorServiceImpl implements BloodDonorService {
    private final BloodDonorRepository repository;

    @Autowired
    public BloodDonorServiceImpl(BloodDonorRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<BloodDonor> getAll() {
        return repository.findAll();
    }
	@Override
	public BloodDonor getByPersonalId(String personalId) {
		return repository.findByPersonalId(personalId).orElse(null);
	}
	public Boolean filledOutQuestions(String personalId) {
		BloodDonor donor = this.getByPersonalId(personalId);
		if(donor==null) {
			return false;
		}
		if(donor.getAnswers().isEmpty()) return false;
		return true;
	}
	@Override
	public BloodDonor update(BloodDonor bloodDonor){
		BloodDonor fromRepo = repository.findByPersonalId(bloodDonor.getPersonalId()).orElse(null);
		if(fromRepo == null) {
			return null;
		}
		BloodDonor swapped = swapValues(fromRepo, bloodDonor);
		
		return repository.save(swapped);
	}
	private BloodDonor swapValues(BloodDonor fromRepo, BloodDonor bloodDonor) {
		fromRepo.setPersonalId(bloodDonor.getPersonalId());
    	fromRepo.setFirstName(bloodDonor.getFirstName());
    	fromRepo.setLastName(bloodDonor.getLastName());
    	fromRepo.setEmail(bloodDonor.getEmail());
    	fromRepo.setPassword(bloodDonor.getPassword());
    	fromRepo.setPhoneNumber(bloodDonor.getPhoneNumber());
    	fromRepo.setGender(bloodDonor.getGender());
    	fromRepo.setOccupation(bloodDonor.getOccupation());
    	fromRepo.setAddress(bloodDonor.getAddress());
    	fromRepo.setInstitution(bloodDonor.getInstitution());
    	return fromRepo;
	}
    @Override
    public BloodDonor getByEmail(String email) {
        return repository.findAllByEmail(email).orElse(null);
    }
}
