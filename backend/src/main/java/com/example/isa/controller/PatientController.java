package com.example.isa.controller;

import com.example.isa.dto.PatientDto;
import com.example.isa.model.Address;
import com.example.isa.model.Gender;
import com.example.isa.model.Patient;
import com.example.isa.service.interfaces.PatientService;
import com.example.isa.service.interfaces.UserService;
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
    @RequestMapping(value="/{personalId}")
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
    @RequestMapping(value="/update", method = RequestMethod.PUT)
    @ApiOperation(value = "Update patient info", httpMethod = "PUT")
    public ResponseEntity<?> updatePatient(@RequestParam PatientDto patientDto){
    	//patientService.
    	return ResponseEntity.ok(null);
    }
    
}
