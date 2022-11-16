package com.example.isa.controller;

import com.example.isa.dto.CredentialsDto;
import com.example.isa.dto.PatientDto;
import com.example.isa.dto.UserDto;
import com.example.isa.dto.UserTokenState;
import com.example.isa.model.*;
import com.example.isa.security.TokenHandler;
import com.example.isa.service.interfaces.RoleService;
import com.example.isa.service.interfaces.UserService;
import com.example.isa.util.converters.PatientConverter;
import org.springframework.http.HttpStatus;
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
    private final PatientConverter patientConverter;

    public AuthController(TokenHandler tokenHandler, AuthenticationManager authenticationManager, UserService userService, RoleService roleService, PasswordEncoder passwordEncoder, PatientConverter patientConverter) {
        this.tokenHandler = tokenHandler;
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.roleService = roleService;
        this.patientConverter = patientConverter;
    }

    @PostMapping("/login")
    public ResponseEntity<UserTokenState> createAuthenticationToken(
            @RequestBody CredentialsDto authenticationRequest, HttpServletResponse response) {
        Authentication authentication = this.authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        User user = (User) authentication.getPrincipal();
        String jwt = this.tokenHandler.generateToken(user.getUsername());
        return ResponseEntity.ok(new UserTokenState(jwt, user.getUsername()));
    }

    @GetMapping("/current")
    public ResponseEntity<UserDto> createAuthenticationToken() {
        var user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return ResponseEntity.ok(new UserDto(user));
    }

    @PostMapping("/register")
    public ResponseEntity<PatientDto> registerPatient(@RequestBody PatientDto dto) {
        if (userService.findByUsername(dto.getEmail()) == null) {
            Patient patient = patientConverter.dtoToEntity(dto);
            patient.setRoles(roleService.findByName("ROLE_PATIENT"));
            userService.register(patient);
            return new ResponseEntity<>(patientConverter.entityToDto(patient), HttpStatus.CREATED);
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