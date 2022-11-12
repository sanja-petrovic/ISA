package com.example.isa.controller;

import com.example.isa.dto.PasswordDto;
import com.example.isa.dto.PatientDto;
import com.example.isa.model.Address;
import com.example.isa.model.Gender;
import com.example.isa.model.Patient;
import com.example.isa.service.interfaces.PatientService;
import com.example.isa.service.interfaces.UserService;
import com.fasterxml.jackson.databind.introspect.TypeResolutionContext.Empty;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.models.media.MediaType;
import lombok.experimental.var;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.convert.DtoInstantiatingConverter;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.method.P;
import org.springframework.web.bind.annotation.*;

import java.security.Provider.Service;
import java.util.List;

@RestController
@Api(value = "/patients")
@RequestMapping(value = "/patients")
public class PatientController {

    private final PatientService patientService;

    @Autowired
    public PatientController(PatientService patientService) {
        this.patientService = patientService;
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(!patient.equals(null)) {
			PatientDto dto = new PatientDto(patient);
	    	return ResponseEntity.ok(dto);
		}
		return (ResponseEntity<?>) ResponseEntity.notFound();
    }
    
    @PutMapping(value="/update")
    @ApiOperation(value = "Update patient info", httpMethod = "PUT")
    public ResponseEntity<?> updatePatient(@RequestBody PatientDto patientDto){
    	Patient patient = new Patient();
    	if(patientDto.equals(null)) {
    		return (ResponseEntity<?>) ResponseEntity.badRequest();
    	}
    	patient = patientDto.convert();
    	Patient retVal = patientService.update(patient);
    	if(retVal == null) {
    		return (ResponseEntity<?>) ResponseEntity.notFound();
    	}
    	return ResponseEntity.ok(retVal);
    }
    @PostMapping(value="/update/password")
    @ApiOperation(value = "Update patient password", httpMethod = "POST")
    public ResponseEntity<?> updatePatientPassword(@RequestBody PasswordDto passwordDto){
    	if(passwordDto == null) {
    		return (ResponseEntity<?>) ResponseEntity.badRequest();
    	}
    	Patient patient = null;
    	try {
    		patient = patientService.getById(passwordDto.getPersonalId());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	if(patient ==null) {
    		return (ResponseEntity<?>) ResponseEntity.notFound();
    	}
    	if(patient.getPassword().equals(passwordDto.getOldPassword())) {
    		patient.setPassword(passwordDto.getNewPassword());
    		Patient retVal = patientService.update(patient);
        	if(retVal == null) {
        		return (ResponseEntity<?>) ResponseEntity.notFound();
        	}
    	}
    	else {
    		return (ResponseEntity<?>) ResponseEntity.badRequest();
    	}
    	return ResponseEntity.ok(null);
    }
    
}
