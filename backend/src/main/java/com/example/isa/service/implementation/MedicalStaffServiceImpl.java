package com.example.isa.service.implementation;

import com.example.isa.model.MedicalStaff;
import com.example.isa.repository.MedicalStaffRepository;
import com.example.isa.service.interfaces.MedicalStaffService;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;

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

    @Override
    public MedicalStaff getById(UUID id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public MedicalStaff updateMedicalStaff(MedicalStaff medicalStaff) {
        if (medicalStaff.getPassword() == null || medicalStaff.getPassword().trim().isEmpty()) {
            //to do
        }
        System.out.printf(String.valueOf(medicalStaff));
        return repository.save(medicalStaff);
    }

}
