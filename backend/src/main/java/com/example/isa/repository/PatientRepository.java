package com.example.isa.repository;

import com.example.isa.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface PatientRepository extends JpaRepository<Patient, UUID> {
	Optional<Patient> findByPersonalId(String personalId);
    Optional<Patient> findAllByEmail(String email);

}
