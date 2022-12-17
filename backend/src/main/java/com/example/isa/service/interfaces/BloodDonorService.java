package com.example.isa.service.interfaces;

import com.example.isa.model.BloodDonor;

import java.util.List;

public interface BloodDonorService {
    public List<BloodDonor> getAll();
    public BloodDonor getByPersonalId(String personalId) throws Exception;
    public BloodDonor update(BloodDonor bloodDonor);
    public BloodDonor getByEmail(String email);
}
