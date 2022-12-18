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
	@ApiOperation(value = "Get all appointments", httpMethod = "GET")
	public ResponseEntity<List<AppointmentDto>> getAll(){
		return ResponseEntity.ok(converter.listToDtoList(appointmentService.getAll()));
	}

	@GetMapping("/{id}")
	@ApiOperation(value = "Get an appointment by id", httpMethod = "GET")
	public ResponseEntity<AppointmentDto> getById(@PathVariable String id){
		return ResponseEntity.ok(converter.entityToDto(appointmentService.getById(UUID.fromString(id))));
	}
	
	@GetMapping("/blood-bank/{id}")
	@ApiOperation(value = "Get all appointments in a blood bank.", httpMethod = "GET")
	public ResponseEntity<List<AppointmentDto>> getAllByBloodBank(@PathVariable String id){
		return ResponseEntity.ok(converter.listToDtoList(appointmentService.getByBloodBank(UUID.fromString(id))));
	}

	@GetMapping("/blood-donor/{id}")
	@ApiOperation(value = "Get all appointments for a blood donor.", httpMethod = "GET")
	public ResponseEntity<List<AppointmentDto>> getAllByBloodDonor(@PathVariable String id){
		return ResponseEntity.ok(converter.listToDtoList(appointmentService.getByBloodDonor(UUID.fromString(id))));
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
