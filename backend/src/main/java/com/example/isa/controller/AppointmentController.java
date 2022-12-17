package com.example.isa.controller;

import java.security.Principal;
import java.util.List;
import java.util.UUID;

import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

	@PostMapping("/schedule/{id}")
	@ApiOperation(value = "Schedule one of the predefined appointments.", httpMethod = "POST")
	public ResponseEntity schedulePredefined(@PathVariable UUID id, Principal user) {
		return ResponseEntity.ok().build();
	}

	@PostMapping("/cancel/{id}")
	@ApiOperation(value = "Cancel a previously scheduled appointment.", httpMethod = "POST")
	public ResponseEntity cancel(@PathVariable UUID id, Principal user) {
		return ResponseEntity.ok().build();
	}

}
