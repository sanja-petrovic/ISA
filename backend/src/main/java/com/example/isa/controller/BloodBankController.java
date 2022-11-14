package com.example.isa.controller;

import com.example.isa.dto.BloodBankDto;
import com.example.isa.dto.BloodBankListDto;
import com.example.isa.dto.BloodBankSearchSortDto;
import com.example.isa.dto.MedicalStaffDto;
import com.example.isa.model.Address;
import com.example.isa.model.BloodBank;
import com.example.isa.model.Gender;
import com.example.isa.model.MedicalStaff;
import com.example.isa.service.interfaces.BloodBankService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
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
        return ResponseEntity.ok(convertListToDto(bloodBankList));
    }

    @PostMapping(value = "/search")
    @ApiOperation(value = "Search, filter and sort blood banks.", httpMethod = "POST")
    public ResponseEntity<BloodBankListDto> search(@RequestBody BloodBankSearchSortDto request) {
        //Search and filter to be implemented later on.
        Sort sort = Sort.by(Sort.Direction.fromString(request.getSortCriteria().getDirection()), request.getSortCriteria().getProperty());
        List<BloodBank> searchedData = service.search(sort, request.getSearchCriteria(), request.getFilterGrade());
        return ResponseEntity.ok(convertListToDto(searchedData));
    }

    private BloodBankListDto convertListToDto(List<BloodBank> bloodBankList) {
        List<BloodBankDto> bloodBankDtoList = bloodBankList.stream().map(bank -> new BloodBankDto(bank.getTitle(), bank.getAddress().getStreet(), bank.getAddress().getCity(), bank.getAddress().getCountry(), bank.getWorkingHours().getIntervalStart().toString(), bank.getWorkingHours().getIntervalEnd().toString(), bank.getDescription(), bank.getAverageGrade())).collect(Collectors.toList());
        BloodBankListDto dto = new BloodBankListDto(bloodBankDtoList);
        return dto;
    }

    @PostMapping(value = "/update")
    @ApiOperation(value = "Update blood bank.", httpMethod = "POST")
    public ResponseEntity<?> update(@RequestBody BloodBankDto bloodBankDto) {
        BloodBank bloodBank = service.getById(UUID.fromString(bloodBankDto.getId()));
        bloodBank.setAddress(new Address(bloodBankDto.getStreet(), bloodBankDto.getCity(), bloodBankDto.getCountry()));
        bloodBank.setDescription(bloodBankDto.getDescription());
        bloodBank.setTitle(bloodBankDto.getTitle());
        bloodBank.setAverageGrade(bloodBankDto.getAverageGrade());
        service.updateBloodBank(bloodBank);
        return ResponseEntity.ok(bloodBank);
    }
}
