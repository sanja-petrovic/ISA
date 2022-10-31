package com.example.isa.controller;

import com.example.isa.dto.CredentialsDto;
import com.example.isa.dto.PatientDto;
import com.example.isa.dto.UserTokenState;
import com.example.isa.model.*;
import com.example.isa.security.TokenHandler;
import com.example.isa.service.interfaces.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@RestController
@Api(value = "/users")
@RequestMapping(value = "/users")
public class UserController {
    private final UserService service;
    private final PasswordEncoder passwordEncoder;

    private final TokenHandler tokenHandler;

    private final AuthenticationManager authenticationManager;

    public UserController(UserService service, PasswordEncoder passwordEncoder, TokenHandler tokenHandler, AuthenticationManager authenticationManager) {
        this.service = service;
        this.passwordEncoder = passwordEncoder;
        this.tokenHandler = tokenHandler;
        this.authenticationManager = authenticationManager;
    }

    @GetMapping
    @ApiOperation(value = "See all users.", httpMethod = "GET")
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @PostMapping
    @ApiOperation(value = "Register a patient.", httpMethod = "POST")
    public ResponseEntity<Patient> registerPatient(@RequestBody PatientDto dto) {
        UserDetails user = service.loadUserByUsername(dto.getEmail());
        if (user != null) {
            Gender gender = dto.getGender().trim().equalsIgnoreCase("female") ? Gender.FEMALE : Gender.MALE;
            Patient patient = Patient.builder()
                    .personalId(dto.getPersonalId())
                    .status(AccountStatus.NOT_VERIFIED)
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
            return new ResponseEntity<>(patient, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(null, HttpStatus.CONFLICT);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<UserTokenState> createAuthenticationToken(
            @RequestBody CredentialsDto authenticationRequest, HttpServletResponse response) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        User user = (User) authentication.getPrincipal();
        String jwt = tokenHandler.generateToken(user.getUsername());
        int expiresIn = tokenHandler.getExpiredIn();
        return ResponseEntity.ok(new UserTokenState(jwt, expiresIn));
    }
}
