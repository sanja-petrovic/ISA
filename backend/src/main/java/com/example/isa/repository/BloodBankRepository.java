package com.example.isa.repository;

import com.example.isa.model.BloodBank;

import org.springframework.data.domain.Sort;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface BloodBankRepository extends JpaRepository<BloodBank, UUID> {
	@Query("from BloodBank b where b.title like CONCAT('%', ?1, '%') and b.averageGrade >= ?2")
	public List<BloodBank> findByTitleLike(String title,double filterGrade, Sort sort);
	
	@Query("from BloodBank b where b.address.city like CONCAT('%', ?1, '%') and b.averageGrade >= ?2")
	public List<BloodBank> findByAddressCityLike(String city,double filterGrade, Sort sort); 
	
	@Query("from BloodBank b where b.title like CONCAT('%', ?1, '%') and b.address.city like CONCAT('%', ?2, '%') and b.averageGrade >= ?3")
	public List<BloodBank> findByTitleLikeAndAddressCityLike(String title,String city, double filterGrade, Sort sort );
	@Query("from BloodBank b where b.averageGrade >= ?1")
	public List<BloodBank> findAllWithFilter(double filterGrade, Sort sort);
}
