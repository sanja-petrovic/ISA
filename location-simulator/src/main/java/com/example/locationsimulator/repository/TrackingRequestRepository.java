package com.example.locationsimulator.repository;

import com.example.locationsimulator.model.TrackingRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface TrackingRequestRepository extends JpaRepository<TrackingRequest, UUID> {
}
