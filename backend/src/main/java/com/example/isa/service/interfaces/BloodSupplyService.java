package com.example.isa.service.interfaces;

import com.example.isa.model.BloodSupply;

import java.util.List;
import java.util.UUID;

public interface BloodSupplyService {
    List<BloodSupply> getAll();

    BloodSupply getById(UUID id);

    BloodSupply update(BloodSupply supplies);
}
