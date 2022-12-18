package com.example.isa.repository;

import com.example.isa.model.BloodDonor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface BloodDonorRepository extends JpaRepository<BloodDonor, UUID> {
	Optional<BloodDonor> findByPersonalId(String personalId);
    Optional<BloodDonor> findAllByEmail(String email);

}
