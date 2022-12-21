package com.example.isa.controller;

import com.example.isa.dto.AdminDto;
import com.example.isa.dto.BloodBankDto;
import com.example.isa.dto.MedicalStaffDto;
import com.example.isa.dto.MedicalStaffListDto;
import com.example.isa.model.*;
import com.example.isa.service.interfaces.MedicalStaffService;
import com.example.isa.service.interfaces.RoleService;
import com.example.isa.service.interfaces.UserService;
import com.example.isa.util.converters.BloodBankConverter;
import com.example.isa.util.converters.MedicalStaffConverter;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.models.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@Api(value = "/medical-staff", tags = "Medical staff")
@RequestMapping(value = "/medical-staff")
public class MedicalStaffController {
    private final MedicalStaffService medicalStaffService;
    private final PasswordEncoder passwordEncoder;
    private final UserService userService;
    private final MedicalStaffConverter converter;
    private final BloodBankConverter bloodBankConverter;
    private final RoleService roleService;

    private final MedicalStaffConverter medicalStaffConverter;
    @Autowired
    public MedicalStaffController(MedicalStaffService medicalStaffService, UserService userService, PasswordEncoder passwordEncoder, MedicalStaffConverter converter, BloodBankConverter bloodBankConverter, RoleService roleService, MedicalStaffConverter medicalStaffConverter) {
        this.medicalStaffService = medicalStaffService;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.converter = converter;
        this.bloodBankConverter = bloodBankConverter;
        this.medicalStaffConverter = medicalStaffConverter;
        this.roleService = roleService;
    }

    @GetMapping
    @ApiOperation(value = "Get all medical staff.", httpMethod = "GET")
    public ResponseEntity<?> getAll() {
        List<MedicalStaff> medicalStaff = medicalStaffService.getAll();
        return ResponseEntity.ok(convertListToDto(medicalStaff));
    }

    @GetMapping(value = "/findById/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Get a member of staff by id.", httpMethod = "GET")
    public ResponseEntity<?> getMedicalStaffById(@PathVariable String id) {
        MedicalStaff medicalStaff = (MedicalStaff)userService.loadUserByUsername(id);
        if (medicalStaff == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(new MedicalStaffDto(medicalStaff));
    }

    private MedicalStaffListDto convertListToDto(List<MedicalStaff> medicalStaffList) {
        List<MedicalStaffDto> medicalStaffDtoList = medicalStaffList.stream().map(converter::entityToDto).collect(Collectors.toList());
        return new MedicalStaffListDto(medicalStaffDtoList);
    }

    @PostMapping
    @ApiOperation(value = "Update a medical staff member's profile.", httpMethod = "POST")
    public ResponseEntity<?> update(@RequestBody MedicalStaffDto medicalStaffDto) {
        MedicalStaff medicalStaff = medicalStaffService.getById(UUID.fromString(medicalStaffDto.getId()));
        medicalStaff.setGender(Gender.valueOf(medicalStaffDto.getGender()));
        medicalStaff.setFirstName(medicalStaffDto.getFirstName());
        medicalStaff.setLastName(medicalStaffDto.getLastName());
        medicalStaff.setPassword(passwordEncoder.encode(medicalStaffDto.getPassword()));
        medicalStaff.setPhoneNumber(medicalStaffDto.getPhoneNumber());
        medicalStaffService.updateMedicalStaff(medicalStaff);
        return ResponseEntity.ok(medicalStaff);
    }

    @GetMapping(value = "/bank")
    @ApiOperation(value = "Get the blood bank that the currently logged in medical staff member works in.", httpMethod = "GET")
    public ResponseEntity<?> getBloodBank(Principal user) {
        MedicalStaff medicalStaff = (MedicalStaff)userService.loadUserByUsername(user.getName());
        return medicalStaff == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(bloodBankConverter.entityToDto(medicalStaff.getBloodBank()));
    }
    @PostMapping(value ="/register")
    @ApiOperation(value = "Register a medical staff member.", httpMethod = "POST")
    public ResponseEntity<?> register(@RequestBody MedicalStaffDto medicalStaffDto){
        MedicalStaff medicalStaff = medicalStaffConverter.dtoToEntity(medicalStaffDto);
        if (userService.findByUsername(medicalStaffDto.getEmail()) == null) {
            medicalStaff.setRole(roleService.findByName("STAFF"));
            medicalStaffService.register(medicalStaff);
            return new ResponseEntity<>((medicalStaff), HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(null, HttpStatus.CONFLICT);
        }
    }
    @GetMapping("/current")
    @PreAuthorize("hasRole('ROLE_STAFF')")
    public ResponseEntity<MedicalStaffDto> getCurrentStaff(Principal principal) {
        Optional<MedicalStaff> staff = medicalStaffService.findByEmail(principal.getName());
        return staff.map(medicalStaff -> ResponseEntity.ok(converter.entityToDto(medicalStaff))).orElseGet(() -> ResponseEntity.notFound().build());
    }
}
