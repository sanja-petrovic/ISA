package com.example.isa.service.interfaces;

import com.example.isa.model.MedicalStaff;
import com.example.isa.model.User;

import java.util.List;

public interface MedicalStaffService {
    public List<User> getAll();
    public void register(MedicalStaff medicalStaff);
}
