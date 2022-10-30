package com.example.isa.controller;

import com.example.isa.dto.PatientDto;
import com.example.isa.model.Address;
import com.example.isa.model.Gender;
import com.example.isa.model.Patient;
import com.example.isa.service.interfaces.PatientService;
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

    @PostMapping
    @ApiOperation(value = "Register a patient.", httpMethod = "POST")
    public ResponseEntity<?> register(@RequestBody PatientDto dto) {
        Gender gender = dto.getGender().trim().toLowerCase().equals("female") ? Gender.FEMALE : Gender.MALE;
        Patient patient = Patient.builder()
                .personalId(dto.getPersonalId())
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .email(dto.getEmail())
                .password(dto.getPassword())
                .phoneNumber(dto.getPhoneNumber())
                .gender(gender)
                .occupation(dto.getOccupation())
                .address(new Address(dto.getStreet(), dto.getNumber(), dto.getCity(), dto.getCountry()))
                .institutionInfo(dto.getInstitutionInfo())
                .build();
        patientService.register(patient);
        return ResponseEntity.ok().build();
    }


}
