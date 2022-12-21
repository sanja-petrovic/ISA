package com.example.isa.repository;

import com.example.isa.model.Supplies;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface SuppliesRepository extends JpaRepository<Supplies, UUID> {
    Supplies findAllById(UUID id);
}
