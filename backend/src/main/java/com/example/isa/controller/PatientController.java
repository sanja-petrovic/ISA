package com.example.isa.controller;

import com.example.isa.dto.PatientDto;
import com.example.isa.model.Patient;
import com.example.isa.service.interfaces.PatientService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Api(value = "/users")
@RequestMapping(value = "/users")
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
        return ResponseEntity.ok().build();
    }

    @PostMapping
    @ApiOperation(value = "Register a patient.", httpMethod = "POST")
    public ResponseEntity<?> register(@RequestBody PatientDto dto) {
        patientService.register(dto);
        return ResponseEntity.ok().build();
    }


}
