package com.example.isa.repository;

import com.example.isa.model.locator.TrackingRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface TrackingRequestRepository extends JpaRepository<TrackingRequest, UUID> {
}
