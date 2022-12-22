package com.example.isa.repository;

import com.example.isa.model.BloodSupply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;
@Repository
public interface BloodSupplyRepository extends JpaRepository<BloodSupply, UUID> {
    BloodSupply findAllById(UUID id);
}
