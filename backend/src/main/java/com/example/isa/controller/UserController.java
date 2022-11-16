package com.example.isa.controller;

import com.example.isa.dto.PasswordDto;
import com.example.isa.model.*;
import com.example.isa.service.interfaces.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

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

    @GetMapping("/current")
    @ApiOperation(value = "Get the logged in user.", httpMethod = "GET")
    public User user(Principal user) {
        if (user != null) {
            return this.service.findByUsername(user.getName());
        } else {
            return null;
        }
    }

    @PostMapping("/update/password")
    @ApiOperation(value = "Update user password", httpMethod = "POST")
    public ResponseEntity<?> updatePassword(@RequestBody PasswordDto passwordDto) {
        if(passwordDto == null) {
            return ResponseEntity.badRequest().build();
        }
        try {
            User user = service.findByPersonalId(passwordDto.getPersonalId());
            if(passwordEncoder.matches(passwordDto.getOldPassword(), user.getPassword())) {
                service.updatePassword(user, passwordEncoder.encode(passwordDto.getNewPassword()));
            }
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().build();
    }

}
