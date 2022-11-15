package com.example.isa.service.implementation;

import com.example.isa.model.MedicalStaff;
import com.example.isa.model.User;
import com.example.isa.repository.MedicalStaffRepository;
import com.example.isa.service.interfaces.MedicalStaffService;

import java.util.List;

public class MedicalStaffServiceImpl implements MedicalStaffService {

    private final MedicalStaffRepository repository;

    public MedicalStaffServiceImpl(MedicalStaffRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<User> getAll() {
    return repository.findAll();
    }

    @Override
    public void register(MedicalStaff medicalStaff) {
         repository.save(medicalStaff);
    }
}
