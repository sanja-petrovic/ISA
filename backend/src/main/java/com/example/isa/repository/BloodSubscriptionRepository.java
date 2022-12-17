package com.example.isa.repository;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.print.attribute.standard.DateTimeAtCompleted;

import org.springframework.cglib.core.TinyBitSet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.isa.model.BloodBank;
import com.example.isa.model.BloodSubscription;

public interface BloodSubscriptionRepository extends JpaRepository<BloodSubscription, UUID>{
	
	@Query("from BloodSubscription sub where sub.active = true and"
			+ " DATE(?1) +sub.deliveryDate-?2-1 = current_date")
	public List<BloodSubscription>findAllUpcomminActive(String date, int dayOffset);
	@Query("from BloodSubscription sub where sub.active = true and"
			+ " DATE(?1)+sub.deliveryDate-1 = current_date")
	public List<BloodSubscription>findAllTodayActive(String date);
}
