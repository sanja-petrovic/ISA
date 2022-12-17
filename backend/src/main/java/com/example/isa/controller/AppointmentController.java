package com.example.isa.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.isa.dto.AppointmentDto;
import com.example.isa.model.Appointment;
import com.example.isa.service.interfaces.AppointmentService;
import com.example.isa.util.converters.AppointmentConverter;

@RestController
@RequestMapping("/appointments")
public class AppointmentController {
	private final AppointmentService appointmentService;
	private final AppointmentConverter converter;
	
	public AppointmentController(AppointmentService service, AppointmentConverter converter) {
		appointmentService = service;
		this.converter = converter;
	}
	
	@GetMapping
	public ResponseEntity<List<AppointmentDto>> getAllByBloodBank(@RequestParam String bankId){
		List<Appointment> appointments = appointmentService.getByBloodBank(UUID.fromString(bankId));
		return ResponseEntity.ok(converter.listToDtoList(appointments));
	}
}
