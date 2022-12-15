package com.example.isa.service.interfaces;

import com.example.isa.model.BloodBank;
import com.example.isa.model.BloodType;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.UUID;

public interface BloodBankService {
    List<BloodBank> getAll();
    boolean registerBank (BloodBank bank);
    List<BloodBank> search(Sort sort, List<String> searchCriteria, String filterGrade);
    BloodBank getById(UUID id);
    List<BloodBank> search(Sort sort, List<String> searchCriteria);
    BloodBank updateBloodBank(BloodBank bloodBank);
    BloodBank findByTitle(String title);
    BloodBank findBankWithMostSupplies(BloodType type, Double amount);
    boolean updateBloodSupplies(BloodBank bloodBank, BloodType type, Double amount);
}
