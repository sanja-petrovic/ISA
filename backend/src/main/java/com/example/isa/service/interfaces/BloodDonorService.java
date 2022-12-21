package com.example.isa.service.interfaces;

import com.example.isa.model.BloodDonor;

import java.util.List;

public interface BloodDonorService {
    List<BloodDonor> getAll();
    BloodDonor getByPersonalId(String personalId);
    BloodDonor update(BloodDonor bloodDonor);
    BloodDonor getByEmail(String email);
    Boolean filledOutQuestions(String personalId);
}
