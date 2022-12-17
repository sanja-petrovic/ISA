package com.example.isa.controller;

import java.security.Principal;
import java.util.List;
import java.util.UUID;

import com.example.isa.service.interfaces.BloodDonorService;
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
	private final BloodDonorService bloodDonorService;
	private final AppointmentConverter converter;
	
	public AppointmentController(AppointmentService service, BloodDonorService bloodDonorService, AppointmentConverter converter) {
		appointmentService = service;
		this.bloodDonorService = bloodDonorService;
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
		appointmentService.schedulePredefined(appointmentService.getById(id), bloodDonorService.getByEmail(user.getName()));
		return ResponseEntity.ok().build();
	}

	@PostMapping("/cancel/{id}")
	@ApiOperation(value = "Cancel a previously scheduled appointment.", httpMethod = "POST")
	public ResponseEntity cancel(@PathVariable UUID id, Principal user) {
		return ResponseEntity.ok().build();
	}

}
