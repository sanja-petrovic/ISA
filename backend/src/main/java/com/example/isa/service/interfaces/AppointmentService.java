package com.example.isa.service.interfaces;

import java.util.List;
import java.util.UUID;

import com.example.isa.model.Appointment;

public interface AppointmentService {
	public List<Appointment> getAll();
	public List<Appointment> getByBloodBank(UUID bankId);
	public List<Appointment> getByBloodDonor(UUID bloodDonorId);
	public Appointment create(Appointment appointment);
	public Appointment update(Appointment appointment);
	public void schedulePredefined(Appointment appointment);
}
