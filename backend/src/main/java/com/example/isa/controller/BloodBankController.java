package com.example.isa.controller;

import com.example.isa.dto.BloodBankDto;
import com.example.isa.dto.BloodBankListDto;
import com.example.isa.model.BloodBank;
import com.example.isa.service.interfaces.BloodBankService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@Api(value = "/bloodbanks")
@RequestMapping(value = "/bloodbanks")
public class BloodBankController {

    private final BloodBankService service;

    @Autowired
    public BloodBankController(BloodBankService service) {
        this.service = service;
    }

    @GetMapping
    @ApiOperation(value = "Get all blood banks.", httpMethod = "GET")
    public ResponseEntity<BloodBankListDto> getAll() {
        List<BloodBank> bloodBankList = service.getAll();
        List<BloodBankDto> bloodBankDtoList = bloodBankList.stream().map(bank -> new BloodBankDto(bank.getTitle(), bank.getAddress().getStreet(), bank.getAddress().getNumber(), bank.getAddress().getCity(), bank.getAddress().getCountry(), bank.getWorkingHours().getIntervalStart().toString(), bank.getWorkingHours().getIntervalEnd().toString(), bank.getDescription(), bank.getAverageGrade())).collect(Collectors.toList());
        BloodBankListDto dto = new BloodBankListDto(bloodBankDtoList);
        return ResponseEntity.ok(dto);
    }
}
