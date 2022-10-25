package com.example.isa.service.interfaces;

import com.example.isa.model.BloodBank;
import org.springframework.data.domain.Sort;

import java.util.List;

public interface BloodBankService {
    public List<BloodBank> getAll();
    public List<BloodBank> search(Sort sort, List<String> searchCriteria);
}
