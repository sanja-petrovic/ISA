package com.example.isa.service.interfaces;

import java.awt.image.BufferedImage;
import java.text.ParseException;
import java.time.LocalDate;
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
	List<Appointment> getByBloodDonor(UUID bloodDonorId, AppointmentStatus status, Sort sort);
	List<Appointment> getAllUpcomingByBloodDonor(UUID bloodDonorId, Sort sort);
	List<Appointment> getUpcomingScheduledByBloodDonor(UUID bloodDonorId, Sort sort);
	List<Appointment> getPastByBloodDonor(UUID bloodDonorId, Sort sort);
	BufferedImage generateQrCodeForAppointment(Appointment appointment);
	Appointment create(Appointment appointment);
	Appointment createScheduled(Appointment appointment);
	Appointment createByDonor(Appointment appointment, BloodDonor donor);
	Appointment save(Appointment appointment);
	void schedulePredefined(Appointment appointment, BloodDonor donor);
	void cancel(Appointment appointment);
	void complete(Appointment appointment);
	void setStatus(Appointment appointment, AppointmentStatus status);
	List<BloodBank> getFreeBloodBanksForDate(String dateTimeString, long duration, Sort sort) throws ParseException;
	List<Appointment> getScheduleByBloodBankInDateRange(String bankId, String range);
	List<Appointment> getAppointmentsInDateRange(List<Appointment> appointments, LocalDate range);
}
