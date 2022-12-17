package com.example.isa.controller;

import com.example.isa.dto.BloodBankDto;
import com.example.isa.dto.BloodBankSearchSortDto;
import com.example.isa.model.Address;
import com.example.isa.model.BloodBank;
import com.example.isa.service.interfaces.BloodBankService;
import com.example.isa.util.converters.BloodBankConverter;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@Api(value = "/blood-banks")
@RequestMapping(value = "/blood-banks")
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
        Sort sort = Sort.by(Sort.Direction.fromString(request.getSortCriteria().getDirection()), request.getSortCriteria().getProperty());
        List<BloodBank> searchedData = service.search(sort, request.getSearchCriteria(), request.getFilterGrade());
        if(searchedData != null) {
            List<BloodBankDto> dtos = searchedData.stream().map(bloodBankConverter::entityToDto).toList();
            return ResponseEntity.ok(dtos);
        } else {
            return ResponseEntity.ok(new ArrayList<BloodBankDto>());
        }
    }


    @PostMapping(value = "/register")
    @ApiOperation(value = "Register a new blood bank.", httpMethod = "POST")
    public ResponseEntity registerBank(@RequestBody BloodBankDto bankDto) {
        BloodBank bank = bloodBankConverter.dtoToEntity(bankDto);
        service.register(bank);
        return ResponseEntity.ok().build();
    }

    @PostMapping(value = "/update")
    @ApiOperation(value = "Update a blood bank.", httpMethod = "POST")
    public ResponseEntity<?> update(@RequestBody BloodBankDto bloodBankDto) {
        BloodBank bloodBank = service.getById(UUID.fromString(bloodBankDto.getId()));
        bloodBank.setAddress(new Address(bloodBankDto.getStreet(), bloodBankDto.getCity(), bloodBankDto.getCountry()));
        bloodBank.setDescription(bloodBankDto.getDescription());
        bloodBank.setTitle(bloodBankDto.getTitle());
        bloodBank.setAverageGrade(bloodBankDto.getAverageGrade());
        service.update(bloodBank);
        return ResponseEntity.ok(bloodBank);
    }
}
