package com.example.isa.service.interfaces;
import com.example.isa.model.MedicalStaff;
import com.example.isa.model.User;

import java.util.List;

import com.example.isa.model.BloodBank;
import com.example.isa.model.MedicalStaff;
import java.util.List;
import java.util.UUID;

public interface MedicalStaffService {
    public List<MedicalStaff> getAll();
    public MedicalStaff getById(UUID id);

    public MedicalStaff updateMedicalStaff(MedicalStaff medicalStaff);
    public void register(MedicalStaff medicalStaff);

    public BloodBank getBloodBank(MedicalStaff medicalStaff);

}
