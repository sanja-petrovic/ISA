package com.example.isa.service.interfaces;

import com.example.isa.model.Patient;

import java.util.List;

public interface PatientService {
    public List<Patient> getAll();
    public Patient getByPersonalId(String personalId) throws Exception;
    public Patient update(Patient patient);
    public Patient getByEmail(String email);
}
