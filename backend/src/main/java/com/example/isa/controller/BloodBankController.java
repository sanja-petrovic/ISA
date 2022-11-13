package com.example.isa.controller;

import com.example.isa.dto.BloodBankDto;
import com.example.isa.dto.BloodBankListDto;
import com.example.isa.dto.BloodBankSearchSortDto;
import com.example.isa.model.BloodBank;
import com.example.isa.service.interfaces.BloodBankService;
import com.example.isa.util.Converters.BloodBankConverter;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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
    public ResponseEntity<BloodBankListDto> getAll() {
        List<BloodBank> bloodBankList = service.getAll();
        return ResponseEntity.ok(convertListToDto(bloodBankList));
    }

    @PostMapping(value = "/search")
    @ApiOperation(value = "Search, filter and sort blood banks.", httpMethod = "POST")
    public ResponseEntity<BloodBankListDto> search(@RequestBody BloodBankSearchSortDto request) {
        //Search and filter to be implemented later on.
        Sort sort = Sort.by(Sort.Direction.fromString(request.getSortCriteria().getDirection()), request.getSortCriteria().getProperty());
        List<BloodBank> searchedData = service.search(sort, request.getSearchCriteria());
        return ResponseEntity.ok(convertListToDto(searchedData));
    }

    private BloodBankListDto convertListToDto(List<BloodBank> bloodBankList) {
        List<BloodBankDto> bloodBankDtoList = bloodBankList.stream().map(bloodBankConverter::entityToDto).collect(Collectors.toList());
        return new BloodBankListDto(bloodBankDtoList);
    }
}
