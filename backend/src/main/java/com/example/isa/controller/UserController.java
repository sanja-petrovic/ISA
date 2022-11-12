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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.security.Principal;

@RestController
@Api(value = "/users")
@RequestMapping(value = "/users")
public class UserController {
    private final UserService service;
    public UserController(UserService service, PasswordEncoder passwordEncoder, TokenHandler tokenHandler, AuthenticationManager authenticationManager) {
        this.service = service;
    }
    @GetMapping
    @ApiOperation(value = "See all users.", httpMethod = "GET")
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/current")
    @ApiOperation(value = "Get the logged in user.", httpMethod = "GET")
    public User user(Principal user) {
        System.out.println(user);
        return this.service.findByUsername(user.getName());
    }
}
