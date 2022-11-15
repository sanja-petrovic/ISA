package com.example.isa.controller;

import com.example.isa.dto.BloodBankDto;
import com.example.isa.dto.BloodBankSearchSortDto;
import com.example.isa.dto.MedicalStaffDto;
import com.example.isa.model.Address;
import com.example.isa.model.BloodBank;
import com.example.isa.model.Gender;
import com.example.isa.model.MedicalStaff;
import com.example.isa.service.interfaces.BloodBankService;
import com.example.isa.util.converters.BloodBankConverter;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@Api(value = "/bloodbanks")
@RequestMapping(value = "/bloodbanks")
public class BloodBankController {

    private final BloodBankService service;
    private final BloodBankConverter bloodBankConverter;

    @Autowired
    public BloodBankController(BloodBankService service, BloodBankConverter bloodBankConverter) {
        this.service = service;
        this.bloodBankConverter = bloodBankConverter;
    }

    @GetMapping
    @ApiOperation(value = "Get all blood banks.", httpMethod = "GET")
    public ResponseEntity<List<BloodBankDto>> getAll() {
        List<BloodBank> bloodBankList = service.getAll();
        List<BloodBankDto> bloodBankDtoList = bloodBankList.stream().map(bloodBankConverter::entityToDto).collect(Collectors.toList());
        return ResponseEntity.ok(bloodBankDtoList);
    }

    @PostMapping(value = "/search")
    @ApiOperation(value = "Search, filter and sort blood banks.", httpMethod = "POST")
    public ResponseEntity<List<BloodBankDto>> search(@RequestBody BloodBankSearchSortDto request) {
        //Search and filter to be implemented later on.
        Sort sort = Sort.by(Sort.Direction.fromString(request.getSortCriteria().getDirection()), request.getSortCriteria().getProperty());
        List<BloodBank> searchedData = service.search(sort, request.getSearchCriteria());
        List<BloodBankDto> dtos = searchedData.stream().map(bloodBankConverter::entityToDto).toList();
        return ResponseEntity.ok(dtos);
    }
}
