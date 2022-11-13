package com.example.isa.service.implementation;

import com.example.isa.model.BloodBank;
import com.example.isa.repository.BloodBankRepository;
import com.example.isa.service.interfaces.BloodBankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class BloodBankServiceImpl implements BloodBankService {
    private final BloodBankRepository repository;

    @Autowired
    public BloodBankServiceImpl(BloodBankRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<BloodBank> getAll() {
        return repository.findAll();
    }

    @Override
    public BloodBank getById(UUID id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public List<BloodBank> search(Sort sort, List<String> searchCriteria) {
        //Searching to be implemented by student 2.
        return repository.findAll(sort);
    }
}
