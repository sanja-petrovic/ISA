package com.example.isa.service.implementation;

import com.example.isa.dto.PatientDto;
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
    public void register(Patient patient) {
        emailSender.send(new Email(patient.getEmail(), "Verify your registration", "To complete your registration, please click on the link bellow."));
        repository.save(patient);
    }
}
