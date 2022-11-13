package com.example.isa.controller;

import com.example.isa.dto.PasswordDto;
import com.example.isa.dto.PatientDto;
import com.example.isa.model.Patient;
import com.example.isa.service.interfaces.PatientService;

import com.example.isa.util.Converters.PatientConverter;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@Api(value = "/patients")
@RequestMapping(value = "/patients")
public class PatientController {

    private final PatientService patientService;
	private final PatientConverter patientConverter;

	@Autowired
    public PatientController(PatientService patientService, PatientConverter patientConverter) {
        this.patientService = patientService;
		this.patientConverter = patientConverter;
	}

    @GetMapping
    @ApiOperation(value = "Get all patients.", httpMethod = "GET")
    public ResponseEntity<List<PatientDto>> getAll() {
        List<Patient> patients = patientService.getAll();
		List<PatientDto> patientDtos = patients.stream().map(patientConverter::entityToDto).toList();
        return ResponseEntity.ok(patientDtos);
    }
    @RequestMapping(value="/get/{personalId}")
    @ApiOperation(value = "Get patient by personalId. (returns DTO)", httpMethod = "GET")
    public ResponseEntity<PatientDto> getByPersonalId(@PathVariable String personalId){
		try {
			Patient patient = patientService.getByPersonalId(personalId);
			PatientDto dto = patientConverter.entityToDto(patient);
			return ResponseEntity.ok(dto);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ResponseEntity.notFound().build();
    }
    
    @PutMapping(value="/update")
    @ApiOperation(value = "Update patient info", httpMethod = "PUT")
    public ResponseEntity<PatientDto> updatePatient(@RequestBody PatientDto patientDto){
    	Patient retVal = patientService.update(patientConverter.dtoToEntity(patientDto));
    	if(retVal == null) {
    		return ResponseEntity.notFound().build();
    	}
    	return ResponseEntity.ok(patientConverter.entityToDto(retVal));
    }
    
}
