package com.example.isa.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.isa.model.Appointment;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, UUID>{
	@Query("from Appointment ap where ap.patient.personalId = ?1")
	public List<Appointment> getByPatient(UUID patientId);
	@Query("from Appointment ap where ap.bloodBank.id = ?1")
	public List<Appointment> getByBloodBank(UUID patientId);
}
