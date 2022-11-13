package com.example.isa.service.implementation;

import com.example.isa.model.*;
import com.example.isa.repository.PatientRepository;
import com.example.isa.service.interfaces.PatientService;
import com.example.isa.util.EmailSender;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PatientServiceImpl implements PatientService {
    private final PatientRepository repository;

    @Autowired
    public PatientServiceImpl(PatientRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Patient> getAll() {
        return repository.findAll();
    }
	@Override
	public Patient getByPersonalId(String personalId) throws Exception {
		return repository.findByPersonalId(personalId).orElseThrow(() -> new Exception(String.format("No user found with personalId '%s'.", personalId)));
	}

	@Override
	public Patient update(Patient patient){
		Patient fromRepo = repository.findByPersonalId(patient.getPersonalId()).orElse(null);
		if(fromRepo == null) {
			return null;
		}
		Patient swapped = swapValues(fromRepo, patient);
		
		return repository.save(swapped);
	}
	private Patient swapValues(Patient fromRepo, Patient patient) {
		fromRepo.setPersonalId(patient.getPersonalId());
    	fromRepo.setFirstName(patient.getFirstName());
    	fromRepo.setLastName(patient.getLastName());
    	fromRepo.setEmail(patient.getEmail());
    	fromRepo.setPassword(patient.getPassword());
    	fromRepo.setPhoneNumber(patient.getPhoneNumber());
    	fromRepo.setGender(patient.getGender());
    	fromRepo.setOccupation(patient.getOccupation());
    	fromRepo.setAddress(patient.getAddress());
    	fromRepo.setInstitution(patient.getInstitution());
    	return fromRepo;
	}
    @Override
    public Patient getByEmail(String email) {
        return repository.findAllByEmail(email).orElse(null);
    }
}
