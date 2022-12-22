package com.example.isa.controller;

import com.example.isa.dto.BloodSupplyDto;
import com.example.isa.model.BloodSupply;
import com.example.isa.repository.BloodSupplyRepository;
import com.example.isa.service.interfaces.BloodSupplyService;
import com.example.isa.util.converters.BloodSupplyConverter;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Api(value = "/blood-supplies", tags = "Blood supplies")
@RequestMapping(value = "/blood-supplies")
public class BloodSupplyController {
    private final BloodSupplyRepository bloodSupplyRepository;
    private final BloodSupplyConverter bloodSupplyConverter;
    private final BloodSupplyService bloodSupplyService;

    @Autowired
    public BloodSupplyController(BloodSupplyConverter bloodSupplyConverter, BloodSupplyService bloodSupplyService, BloodSupplyRepository bloodSupplyRepository){
        this.bloodSupplyConverter = bloodSupplyConverter;
        this.bloodSupplyRepository = bloodSupplyRepository;
        this.bloodSupplyService = bloodSupplyService;
    }

    @GetMapping
    @ApiOperation(value = "Get all blood supplies", httpMethod = "GET")
    public ResponseEntity<List<BloodSupplyDto>> getAll(){
        List<BloodSupply> bloodSupplies = bloodSupplyService.getAll();
        List<BloodSupplyDto> bloodSupplyDtoList = bloodSupplies.stream().map(bloodSupplyConverter::entityToDto).toList();
        return ResponseEntity.ok(bloodSupplyDtoList);
    }
    @PutMapping(value = "/update")
    @PreAuthorize("hasRole('ROLE_STAFF')")
    @ApiOperation(value = "Update blood supplies", httpMethod = "PUT")
    public ResponseEntity<BloodSupplyDto> update(@RequestBody BloodSupplyDto bloodSupplyDto){
        BloodSupply retVal = bloodSupplyService.update(bloodSupplyConverter.dtoToEntity(bloodSupplyDto));
        return retVal == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(bloodSupplyConverter.entityToDto(retVal));
    }
}
