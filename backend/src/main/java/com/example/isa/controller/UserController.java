package com.example.isa.controller;

import com.example.isa.dto.PatientDto;
import com.example.isa.model.Address;
import com.example.isa.model.Gender;
import com.example.isa.model.Patient;
import com.example.isa.service.interfaces.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@Api(value = "/users")
@RequestMapping(value = "/users")
public class UserController {
    private final UserService service;
    private final PasswordEncoder passwordEncoder;

    public UserController(UserService service, PasswordEncoder passwordEncoder) {
        this.service = service;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping
    @ApiOperation(value = "See all users.", httpMethod = "GET")
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @PostMapping
    @ApiOperation(value = "Register a patient.", httpMethod = "POST")
    public ResponseEntity<?> registerPatient(@RequestBody PatientDto dto) {
        Gender gender = dto.getGender().trim().equalsIgnoreCase("female") ? Gender.FEMALE : Gender.MALE;
        Patient patient = Patient.builder()
                .personalId(dto.getPersonalId())
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .email(dto.getEmail())
                .password(passwordEncoder.encode(dto.getPassword()))
                .phoneNumber(dto.getPhoneNumber())
                .gender(gender)
                .occupation(dto.getOccupation())
                .address(new Address(dto.getStreet(), dto.getNumber(), dto.getCity(), dto.getCountry()))
                .institutionInfo(dto.getInstitutionInfo())
                .build();
        service.register(patient);
        return ResponseEntity.ok().build();
    }
}
