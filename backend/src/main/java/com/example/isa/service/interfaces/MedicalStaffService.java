package com.example.isa.service.interfaces;
import com.example.isa.model.MedicalStaff;
import com.example.isa.model.User;

import java.util.List;

import com.example.isa.model.BloodBank;
import com.example.isa.model.MedicalStaff;
import java.util.List;
import java.util.UUID;

public interface MedicalStaffService {
    List<MedicalStaff> getAll();
    MedicalStaff getById(UUID id);

    MedicalStaff updateMedicalStaff(MedicalStaff medicalStaff);
    boolean register(MedicalStaff medicalStaff);

    BloodBank getBloodBank(MedicalStaff medicalStaff);

}
