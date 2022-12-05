package com.example.isa.repository;

import com.example.isa.model.BloodRequest;
import com.example.isa.model.BloodRequestStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface BloodRequestRepository extends JpaRepository<BloodRequest, UUID> {
}
