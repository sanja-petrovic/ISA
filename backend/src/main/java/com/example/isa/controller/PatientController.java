package com.example.isa.controller;

import com.example.isa.dto.PasswordDto;
import com.example.isa.dto.PatientDto;
import com.example.isa.model.Patient;
import com.example.isa.service.interfaces.PatientService;
import com.example.isa.util.EntityDtoConverter;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Api(value = "/patients")
@RequestMapping(value = "/patients")
public class PatientController {

    private final PatientService patientService;
	private final EntityDtoConverter entityDtoConverter;

    @Autowired
    public PatientController(PatientService patientService, EntityDtoConverter entityDtoConverter) {
        this.patientService = patientService;
		this.entityDtoConverter = entityDtoConverter;
	}

    @GetMapping
    @ApiOperation(value = "Get all patients.", httpMethod = "GET")
    public ResponseEntity<?> getAll() {
        List<Patient> patients = patientService.getAll();
        return ResponseEntity.ok(patients);
    }
    @RequestMapping(value="/get/{personalId}")
    @ApiOperation(value = "Get patient by personalId. (returns DTO)", httpMethod = "GET")
    public ResponseEntity<?> getByPersonalId(@PathVariable String personalId){
    	Patient patient = null;
		try {
			patient = patientService.getById(personalId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(patient != null) {
			PatientDto dto = new PatientDto(patient);
	    	return ResponseEntity.ok(dto);
		}
		return (ResponseEntity<?>) ResponseEntity.notFound();
    }
    
    @PutMapping(value="/update")
    @ApiOperation(value = "Update patient info", httpMethod = "PUT")
    public ResponseEntity<?> updatePatient(@RequestBody PatientDto patientDto){
    	Patient patient = entityDtoConverter.DtoToPatient(patientDto);
    	Patient retVal = patientService.update(patient);
    	if(retVal == null) {
    		return ResponseEntity.notFound().build();
    	}
    	return ResponseEntity.ok(retVal);
    }
    @PostMapping(value="/update/password")
    @ApiOperation(value = "Update patient password", httpMethod = "POST")
    public ResponseEntity<?> updatePatientPassword(@RequestBody PasswordDto passwordDto){
    	if(passwordDto == null) {
    		return ResponseEntity.badRequest().build();
    	}
    	Patient patient = null;
    	try {
    		patient = patientService.getById(passwordDto.getPersonalId());
		} catch (Exception e) {
			e.printStackTrace();
		}
    	if(patient == null) {
    		return ResponseEntity.notFound().build();
    	}
    	if(patient.getPassword().equals(passwordDto.getOldPassword())) {
    		patient.setPassword(passwordDto.getNewPassword());
    		Patient retVal = patientService.update(patient);
        	if(retVal == null) {
        		return ResponseEntity.notFound().build();
        	}
    	}
    	else {
    		return ResponseEntity.badRequest().build();
    	}
    	return ResponseEntity.ok(null);
    }
    
}
