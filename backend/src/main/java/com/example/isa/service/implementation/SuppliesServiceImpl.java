package com.example.isa.service.implementation;

import com.example.isa.model.Supplies;
import com.example.isa.repository.SuppliesRepository;
import com.example.isa.service.interfaces.SuppliesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class SuppliesServiceImpl implements SuppliesService {
    private final SuppliesRepository repository;
    @Autowired
    public SuppliesServiceImpl(SuppliesRepository repository) {this.repository = repository;}

    @Override
    public List<Supplies> getAll() {return repository.findAll();}

    @Override
    public Supplies getById(UUID id) {
        return repository.findAllById(id);
    }

    @Override
    public Supplies update(Supplies supplies){
        Supplies fromRepo = getById(supplies.getId());
        if(fromRepo == null){
            return null;
        }
        Supplies swapped = swapValues(fromRepo, supplies);

        return repository.save(swapped);
    }
    private Supplies swapValues(Supplies fromRepo, Supplies supplies){
        fromRepo.setAmount(supplies.getAmount());
        fromRepo.setId(supplies.getId());
        fromRepo.setName(supplies.getName());
        return fromRepo;
    }
}
