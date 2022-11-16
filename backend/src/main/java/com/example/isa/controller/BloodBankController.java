package com.example.isa.controller;

import com.example.isa.dto.BloodBankDto;
import com.example.isa.dto.BloodBankSearchSortDto;
import com.example.isa.model.Address;
import com.example.isa.model.BloodBank;
import com.example.isa.model.Interval;
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

import javax.swing.text.DateFormatter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
<<<<<<< HEAD
        List<BloodBank> searchedData = service.search(sort, request.getSearchCriteria(), request.getFilterGrade());
        return ResponseEntity.ok(convertListToDto(searchedData));
    }

    @PostMapping(value = "/registerBank")
    @ApiOperation(value = "register blood bank.", httpMethod = "POST")
    public ResponseEntity registerBank(@RequestBody BloodBankDto bankDto) {
        BloodBank bank = new BloodBank(bankDto.getTitle(),new Address(bankDto.getStreet(), bankDto.getCity(), bankDto.getCountry()), bankDto.getDescription());
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        try {
            bank.setWorkingHours(new Interval( format.parse("2022-03-03 " + bankDto.getWorkingHoursStart()), format.parse("2022-03-03 " + bankDto.getWorkingHoursEnd())));
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        return ResponseEntity.ok(service.registerBank(bank));
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
=======
        List<BloodBank> searchedData = service.search(sort, request.getSearchCriteria());
        List<BloodBankDto> dtos = searchedData.stream().map(bloodBankConverter::entityToDto).toList();
        return ResponseEntity.ok(dtos);
>>>>>>> development
    }
}
