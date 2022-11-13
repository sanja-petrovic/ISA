package com.example.isa.repository;

import com.example.isa.model.BloodBank;

import org.springframework.data.domain.Sort;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface BloodBankRepository extends JpaRepository<BloodBank, UUID> {
	//@Query("from BloodBank b where b.title like CONCAT('%', ?1, '%')")
	public List<BloodBank> findByTitleLike(String title, Sort sort);
	
	//@Query("from BloodBank b where b.address.city like CONCAT('%', ?1, '%')")
	public List<BloodBank> findByAddressCityLike(String city, Sort sort); 
	
	//@Query("from BloodBank b where b.title like CONCAT('%', ?1, '%') and b.address.city like CONCAT('%', ?2, '%')")
	public List<BloodBank> findByTitleLikeAndAddressCityLike(String title,String city,Sort sort );
}
