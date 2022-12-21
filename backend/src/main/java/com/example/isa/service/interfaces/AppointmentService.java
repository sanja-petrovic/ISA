package com.example.isa.service.interfaces;

import java.text.ParseException;
import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Sort;

import com.example.isa.model.Appointment;
import com.example.isa.model.BloodBank;
import com.example.isa.model.AppointmentStatus;
import com.example.isa.model.BloodDonor;

public interface AppointmentService {
	List<Appointment> getAll();
	Appointment getById(UUID appointmentId);
	List<Appointment> getByBloodBank(UUID bankId);
	List<Appointment> getByBloodBank(String title);
	List<Appointment> getUnscheduledByBloodBank(UUID bloodBankId);
	List<Appointment> getByBloodDonor(UUID bloodDonorId);
	List<Appointment> getByBloodDonor(UUID bloodDonorId, AppointmentStatus status);
	List<Appointment> getUpcomingByBloodDonor(UUID bloodDonorId);
	Appointment create(Appointment appointment);
	Appointment createScheduled(Appointment appointment);
	Appointment createByDonor(Appointment appointment, BloodDonor donor);
	Appointment update(Appointment appointment);
	void schedulePredefined(Appointment appointment, BloodDonor donor);
	void cancel(Appointment appointment);
	public List<BloodBank> checkFreeBanksForDate(String dateTimeString, long duration, Sort sort) throws ParseException;
}
