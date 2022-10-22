package com.example.isa.service.implementation;

import com.example.isa.dto.PatientDto;
import com.example.isa.model.Account;
import com.example.isa.model.Patient;
import com.example.isa.repository.PatientRepository;
import com.example.isa.service.interfaces.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
    public void register(PatientDto dto) {
        Account account = new Account();
        account.setFirstName(dto.getFirstName());
        account.setLastName(dto.getLastName());
        Patient patient = new Patient();
        repository.save(patient);
    }
}
