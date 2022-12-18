package com.example.isa.service.interfaces;

import com.example.isa.model.BloodBank;
import com.example.isa.model.BloodType;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.UUID;

public interface BloodBankService {
    List<BloodBank> getAll();
    void register(BloodBank bank);
    List<BloodBank> search(Sort sort, List<String> searchCriteria, String filterGrade);
    BloodBank getById(UUID id);

    BloodBank update(BloodBank bloodBank);
    BloodBank findByTitle(String title);
    BloodBank findBankWithMostSupplies(BloodType type, Double amount);
    boolean checkBloodSupply(BloodBank bloodBank, BloodType type, Double amount);
    void updateBloodSupply(BloodBank bloodBank, BloodType type, Double amount);
}
