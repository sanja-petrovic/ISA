package com.example.isa.service.interfaces;

import com.example.isa.model.BloodBank;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.UUID;

public interface BloodBankService {
    public List<BloodBank> getAll();
    public boolean registerBank (BloodBank bank);
    public List<BloodBank> search(Sort sort, List<String> searchCriteria, String filterGrade);
    public BloodBank getById(UUID id);
    public List<BloodBank> search(Sort sort, List<String> searchCriteria);
    public BloodBank updateBloodBank(BloodBank bloodBank);
    public BloodBank findByTitle(String title);
}
