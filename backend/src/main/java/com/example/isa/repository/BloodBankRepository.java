package com.example.isa.repository;

import com.example.isa.model.BloodBank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface BloodBankRepository extends JpaRepository<BloodBank, UUID> {

}
