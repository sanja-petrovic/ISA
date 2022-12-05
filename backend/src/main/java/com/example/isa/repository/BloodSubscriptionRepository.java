package com.example.isa.repository;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.isa.model.BloodBank;
import com.example.isa.model.BloodSubscription;

public interface BloodSubscriptionRepository extends JpaRepository<BloodSubscription, UUID>{
	
}
