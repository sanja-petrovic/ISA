package com.example.isa.controller;

import com.example.isa.dto.MedicalStaffDto;
import com.example.isa.dto.MedicalStaffListDto;
import com.example.isa.model.MedicalStaff;
import com.example.isa.service.interfaces.MedicalStaffService;
import com.example.isa.service.interfaces.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@Api(value = "/medicalStaff")
@RequestMapping(value = "/medical_staff")
public class MedicalStaffController {
    private final MedicalStaffService medicalStaffService;

    private final UserService userService;

    @Autowired
    public MedicalStaffController(MedicalStaffService medicalStaffService, UserService userService) {
        this.medicalStaffService = medicalStaffService;
        this.userService = userService;
    }

    @GetMapping
    @ApiOperation(value = "Get all medical staff.", httpMethod = "GET")
    public ResponseEntity<?> getAll() {
        List<MedicalStaff> patients = medicalStaffService.getAll();
        return ResponseEntity.ok(patients);
    }

    @GetMapping(value = "/findById/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Get medical staff by id.", httpMethod = "GET")
    //@PreAuthorize("hasRole('ROLE_STAFF')")
    public ResponseEntity<?> getMedicalStaffById(String id) {
        MedicalStaff medicalStaff = (MedicalStaff)userService.loadUserByUsername(id);
        if (medicalStaff == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(medicalStaff);
    }

    private MedicalStaffListDto convertListToDto(List<MedicalStaff> medicalStaffList) {
        List<MedicalStaffDto> medicalStaffDtoList = medicalStaffList.stream().map(medicalStaff -> new MedicalStaffDto(medicalStaff.getPersonalId(), medicalStaff.getFirstName(), medicalStaff.getLastName(), medicalStaff.getEmail(), medicalStaff.getPassword(), medicalStaff.getPhoneNumber(), medicalStaff.getGender().toString(), "",  "", "","", "","","")).collect(Collectors.toList());
        MedicalStaffListDto dto = new MedicalStaffListDto(medicalStaffDtoList);
        return dto;
    }
}
