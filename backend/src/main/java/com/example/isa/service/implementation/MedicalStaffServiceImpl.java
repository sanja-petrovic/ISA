package com.example.isa.service.implementation;

import com.example.isa.model.MedicalStaff;
import com.example.isa.repository.MedicalStaffRepository;
import com.example.isa.service.interfaces.MedicalStaffService;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class MedicalStaffServiceImpl implements MedicalStaffService {
    private  final MedicalStaffRepository repository;

    public MedicalStaffServiceImpl(MedicalStaffRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<MedicalStaff> getAll() {
        return repository.findAll();
    }
}
