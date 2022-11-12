package com.example.isa.service.implementation;

import com.example.isa.model.*;
import com.example.isa.repository.PatientRepository;
import com.example.isa.service.interfaces.PatientService;
import com.example.isa.util.EmailSender;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PatientServiceImpl implements PatientService {
    private final PatientRepository repository;
    private final EmailSender emailSender;

    @Autowired
    public PatientServiceImpl(PatientRepository repository, EmailSender emailSender) {
        this.repository = repository;
        this.emailSender = emailSender;
    }

    @Override
    public List<Patient> getAll() {
        return repository.findAll();
    }
	@Override
	public Patient getById(String personalId) throws Exception {
		Patient patient =  repository.findByPersonalId(personalId);
		if(patient.equals(null)) {
			throw new Exception(String.format("No user found with personalId '%s'.", personalId));
		}
		else {
			return patient;
		}
	}

	@Override
	public Patient update(Patient patient){
		Patient fromRepo = repository.findByPersonalId(patient.getPersonalId());
		if(fromRepo == null) {
			return null;
		}
		Patient swapped = swapValues(fromRepo, patient);
		
		return repository.save(swapped);
	}
	private Patient swapValues(Patient fromRepo, Patient patient) {
		Patient retVal = fromRepo;
		retVal.setPersonalId(patient.getPersonalId());
    	retVal.setFirstName(patient.getFirstName());
    	retVal.setLastName(patient.getLastName());
    	retVal.setEmail(patient.getEmail());
    	retVal.setPassword(patient.getPassword());
    	retVal.setPhoneNumber(patient.getPhoneNumber());
    	retVal.setGender(patient.getGender());
    	retVal.setOccupation(patient.getOccupation());
    	retVal.setAddress(patient.getAddress());
    	retVal.setInstitution(patient.getInstitution());
    	return retVal;
	}
    @Override
    public Patient getByEmail(String email) {
        return repository.findAllByEmail(email);
    }
}
