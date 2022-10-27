package com.example.isa.service.interfaces;

import com.example.isa.dto.PatientDto;
import com.example.isa.model.Patient;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface PatientService {
    public List<Patient> getAll();
    public void register(Patient patient);
}
