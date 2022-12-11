package com.example.isa.repository;

import java.time.LocalDate;
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
	public List<Appointment> getByBloodBank(UUID bloodBankId);
	@Query("from Appointment ap where trunc(ap.dateTime) = ?1")
	public List<Appointment> getForDate(LocalDate date);
	@Query("from Appointment ap where  ap.bloodBank.id = ?1 and trunc(ap.dateTime) = ?2")
	public List<Appointment> getByBloodBankForDate(UUID bloodBankId,LocalDate date);

	
}
