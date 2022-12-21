package com.example.isa.service.interfaces;

import com.example.isa.model.Supplies;

import java.util.List;
import java.util.UUID;

public interface SuppliesService {
    List<Supplies> getAll();

    Supplies getById(UUID id);

    Supplies update(Supplies supplies);
}
