package com.example.isa.controller;

import com.example.isa.dto.BloodBankDto;
import com.example.isa.dto.MedicalStaffDto;
import com.example.isa.dto.MedicalStaffListDto;
import com.example.isa.model.Address;
import com.example.isa.model.BloodBank;
import com.example.isa.model.Gender;
import com.example.isa.model.MedicalStaff;
import com.example.isa.service.interfaces.MedicalStaffService;
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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@Api(value = "/medicalStaff")
@RequestMapping(value = "/api/medicalStaff")
public class MedicalStaffController {
    private final MedicalStaffService medicalStaffService;
    private final PasswordEncoder passwordEncoder;
    private final UserService userService;
    private final MedicalStaffConverter converter;
    private final BloodBankConverter bloodBankConverter;

    @Autowired
    public MedicalStaffController(MedicalStaffService medicalStaffService, UserService userService, PasswordEncoder passwordEncoder, MedicalStaffConverter converter, BloodBankConverter bloodBankConverter) {
        this.medicalStaffService = medicalStaffService;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.converter = converter;
        this.bloodBankConverter = bloodBankConverter;
    }

    @GetMapping
    @ApiOperation(value = "Get all medical staff.", httpMethod = "GET")
    public ResponseEntity<?> getAll() {
        List<MedicalStaff> medicalStaff = medicalStaffService.getAll();
        return ResponseEntity.ok(convertListToDto(medicalStaff));
    }

    @GetMapping(value = "/findById/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Get medical staff by id.", httpMethod = "GET")
    //@PreAuthorize("hasRole('ROLE_STAFF')")
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
    @ApiOperation(value = "Update medical staff profile.", httpMethod = "POST")
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
    @ApiOperation(value = "Get blood bank by medical staff id.", httpMethod = "GET")
    //@PreAuthorize("hasRole('ROLE_STAFF')")
    public ResponseEntity<?> getBloodBank(Principal user) {
        MedicalStaff medicalStaff = (MedicalStaff)userService.loadUserByUsername(user.getName());
        if (medicalStaff == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(bloodBankConverter.entityToDto(medicalStaff.getBloodBank()));
    }
    @PostMapping(value ="/register")
    @ApiOperation(value = "Register medical staff profile.", httpMethod = "POST")
    public ResponseEntity<?> register(@RequestBody MedicalStaffDto medicalStaffDto){
        MedicalStaff medicalStaff = mapMedicalStaff(medicalStaffDto);
        return ResponseEntity.ok(medicalStaffService.register(medicalStaff));
    }
    public MedicalStaff mapMedicalStaff(MedicalStaffDto medicalStaffDto){
        MedicalStaff medicalStaff = new MedicalStaff();
        medicalStaff.setPersonalId(medicalStaffDto.getPersonalId());
        medicalStaff.setFirstName(medicalStaffDto.getFirstName());
        medicalStaff.setLastName(medicalStaffDto.getLastName());
        medicalStaff.setPassword(passwordEncoder.encode(medicalStaffDto.getPassword()));
        medicalStaff.setPhoneNumber(medicalStaffDto.getPhoneNumber());
        medicalStaff.setEmail(medicalStaffDto.getEmail());
        medicalStaff.setBloodBank(new BloodBank());
        medicalStaff.getBloodBank().setId(UUID.fromString(medicalStaffDto.getBloodBankId()));
        return medicalStaff;
    }
}
