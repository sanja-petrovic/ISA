package com.example.isa.controller;

import com.example.isa.dto.BloodDonorDto;
import com.example.isa.model.BloodDonor;
import com.example.isa.service.interfaces.BloodDonorService;

import com.example.isa.util.converters.BloodDonorConverter;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Api(value = "/blood-donors")
@RequestMapping(value = "/blood-donors")
public class BloodDonorController {

    private final BloodDonorService bloodDonorService;
	private final BloodDonorConverter bloodDonorConverter;

	@Autowired
    public BloodDonorController(BloodDonorService bloodDonorService, BloodDonorConverter bloodDonorConverter) {
        this.bloodDonorService = bloodDonorService;
		this.bloodDonorConverter = bloodDonorConverter;
	}

    @GetMapping
    @ApiOperation(value = "Get all blood donors.", httpMethod = "GET")
    public ResponseEntity<List<BloodDonorDto>> getAll() {
        List<BloodDonor> bloodDonors = bloodDonorService.getAll();
		List<BloodDonorDto> bloodDonorDtos = bloodDonors.stream().map(bloodDonorConverter::entityToDto).toList();
        return ResponseEntity.ok(bloodDonorDtos);
    }
    @RequestMapping(value="/get/{personalId}")
    @ApiOperation(value = "Get a blood donor by personal ID.", httpMethod = "GET")
    public ResponseEntity<BloodDonorDto> getByPersonalId(@PathVariable String personalId){
		try {
			BloodDonor bloodDonor = bloodDonorService.getByPersonalId(personalId);
			BloodDonorDto dto = bloodDonorConverter.entityToDto(bloodDonor);
			return ResponseEntity.ok(dto);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ResponseEntity.notFound().build();
    }
    
    @PutMapping(value="/update")
    @ApiOperation(value = "Update a blood donor's information", httpMethod = "PUT")
    public ResponseEntity<BloodDonorDto> update(@RequestBody BloodDonorDto bloodDonorDto){
    	BloodDonor retVal = bloodDonorService.update(bloodDonorConverter.dtoToEntity(bloodDonorDto));
		return retVal == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(bloodDonorConverter.entityToDto(retVal));
    }
    
}
