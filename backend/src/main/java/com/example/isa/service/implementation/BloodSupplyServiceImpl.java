package com.example.isa.service.implementation;

import com.example.isa.model.BloodSupply;
import com.example.isa.repository.BloodSupplyRepository;
import com.example.isa.service.interfaces.BloodSupplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class BloodSupplyServiceImpl implements BloodSupplyService {
    private final BloodSupplyRepository repository;

    @Autowired
    public BloodSupplyServiceImpl(BloodSupplyRepository repository){ this.repository = repository;}
    @Override
    public List<BloodSupply> getAll() {return repository.findAll();}

    @Override
    public BloodSupply getById(UUID id) {return  repository.findAllById(id);}

    @Override
    public BloodSupply update(BloodSupply supplies) {
        BloodSupply fromRepo = getById(supplies.getId());
        repository.delete(fromRepo);
        if(fromRepo == null){
            return null;
        }
        BloodSupply swapped = swapValues(fromRepo, supplies);

        repository.delete(fromRepo);
        return repository.save(swapped);
    }
    private BloodSupply swapValues(BloodSupply fromRepo, BloodSupply supplies){
        fromRepo.setAmount(supplies.getAmount());
        fromRepo.setId(supplies.getId());
        fromRepo.setType(supplies.getType());
        return fromRepo;
    }
}
