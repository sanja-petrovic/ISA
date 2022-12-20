package com.example.isa.controller;

import com.example.isa.dto.BloodDonorDto;
import com.example.isa.dto.UserDto;
import com.example.isa.model.BloodDonor;
import com.example.isa.model.User;
import com.example.isa.repository.BloodDonorRepository;
import com.example.isa.service.interfaces.BloodDonorService;

import com.example.isa.util.converters.BloodDonorConverter;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@RestController
@Api(value = "/blood-donors")
@RequestMapping(value = "/blood-donors")
public class BloodDonorController {
    private final BloodDonorService bloodDonorService;
	private final BloodDonorConverter bloodDonorConverter;
    private final BloodDonorRepository bloodDonorRepository;

    @Autowired
    public BloodDonorController(BloodDonorService bloodDonorService, BloodDonorConverter bloodDonorConverter,
                                BloodDonorRepository bloodDonorRepository) {
        this.bloodDonorService = bloodDonorService;
		this.bloodDonorConverter = bloodDonorConverter;
        this.bloodDonorRepository = bloodDonorRepository;
    }

    @GetMapping
    @ApiOperation(value = "Get all blood donors.", httpMethod = "GET")
    public ResponseEntity<List<BloodDonorDto>> getAll() {
        List<BloodDonor> bloodDonors = bloodDonorService.getAll();
		List<BloodDonorDto> bloodDonorDtos = bloodDonors.stream().map(bloodDonorConverter::entityToDto).toList();
        return ResponseEntity.ok(bloodDonorDtos);
    }
    @RequestMapping(value="/{personalId}")
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
    @RequestMapping(value="questions/{personalId}")
    @ApiOperation(value = "Get info about filled questions by personal ID.", httpMethod = "GET")
    public ResponseEntity<Boolean> filledOutQuestions(@PathVariable String personalId){
		try {
			if(bloodDonorService.filledOutQuestions(personalId)) return ResponseEntity.ok(true);
			return ResponseEntity.ok(false);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ResponseEntity.notFound().build();
    }
    
    @GetMapping("/current")
    @PreAuthorize("hasRole('ROLE_DONOR')")
    public ResponseEntity<BloodDonorDto> getCurrentBloodDonor(Principal principal) {
        Optional<BloodDonor> bloodDonor = bloodDonorRepository.findAllByEmail(principal.getName());
        return bloodDonor.map(donor -> ResponseEntity.ok(bloodDonorConverter.entityToDto(donor))).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping(value="/update")
    @PreAuthorize("hasRole('ROLE_DONOR')")
    @ApiOperation(value = "Update a blood donor's information", httpMethod = "PUT")
    public ResponseEntity<BloodDonorDto> update(@RequestBody BloodDonorDto bloodDonorDto){
    	BloodDonor retVal = bloodDonorService.update(bloodDonorConverter.dtoToEntity(bloodDonorDto));
		return retVal == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(bloodDonorConverter.entityToDto(retVal));
    }
    
}
