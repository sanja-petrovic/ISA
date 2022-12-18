package com.example.isa.service.interfaces;

import java.util.List;
import java.util.UUID;

import com.example.isa.model.Appointment;
import com.example.isa.model.BloodDonor;

public interface AppointmentService {
	List<Appointment> getAll();
	Appointment getById(UUID appointmentId);
	List<Appointment> getByBloodBank(UUID bankId);
	List<Appointment> getByBloodDonor(UUID bloodDonorId);
	Appointment create(Appointment appointment);
	Appointment update(Appointment appointment);
	void schedulePredefined(Appointment appointment, BloodDonor donor);
	void cancel(Appointment appointment);
}
