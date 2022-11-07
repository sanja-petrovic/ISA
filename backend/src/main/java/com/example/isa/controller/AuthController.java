package com.example.isa.controller;

import com.example.isa.dto.CredentialsDto;
import com.example.isa.dto.PatientDto;
import com.example.isa.dto.UserTokenState;
import com.example.isa.model.*;
import com.example.isa.security.TokenHandler;
import com.example.isa.service.interfaces.RoleService;
import com.example.isa.service.interfaces.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping(value = "/auth")
public class AuthController {
    private final TokenHandler tokenHandler;
    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;

    public AuthController(TokenHandler tokenHandler, AuthenticationManager authenticationManager, UserService userService, RoleService roleService, PasswordEncoder passwordEncoder) {
        this.tokenHandler = tokenHandler;
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/login")
    public ResponseEntity<UserTokenState> createAuthenticationToken(
            @RequestBody CredentialsDto authenticationRequest, HttpServletResponse response) {
        Authentication authentication = this.authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        User user = (User) authentication.getPrincipal();
        String jwt = this.tokenHandler.generateToken(user.getUsername());
        int expiresIn = this.tokenHandler.getExpiredIn();
        return ResponseEntity.ok(new UserTokenState(jwt, expiresIn));
    }

    @PostMapping("/register")
    public ResponseEntity<Patient> registerPatient(@RequestBody PatientDto dto) {
        User user = userService.findByUsername(dto.getEmail());
        if (user == null) {
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
                    .address(new Address(dto.getHomeAddress(), dto.getCity(), dto.getCountry()))
                    .institutionInfo(dto.getInstitution())
                    .roles(roleService.findByName("ROLE_PATIENT"))
                    .build();
            userService.register(patient);
            return new ResponseEntity<>(patient, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(null, HttpStatus.CONFLICT);
        }
    }

    @PostMapping("/verify/{email}")
    public ResponseEntity<?> verifyAccount(@PathVariable String email) {
        User user = userService.findByUsername(email);
        if(user == null) {
            return ResponseEntity.status(404).body("Account with the given email does not exist.");
        } else if(user.isVerified()) {
            return ResponseEntity.status(403).body("Account already verified.");
        } else {
            userService.verifyAccount(user);
            return ResponseEntity.ok().build();
        }
    }
}
