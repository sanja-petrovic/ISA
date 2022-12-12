package com.example.isa.controller;

import com.example.isa.dto.BloodRequestDto;
import com.example.isa.dto.BloodSupplyDto;
import com.example.isa.service.interfaces.BloodRequestService;

import java.text.ParseException;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/bloodrequests")
public class BloodRequestController {

    private final BloodRequestService bloodRequestService;

    public BloodRequestController(BloodRequestService bloodRequestService) {
        this.bloodRequestService = bloodRequestService;
    }

    @PostMapping(value = "/test")
    public ResponseEntity<?> test() {
        this.bloodRequestService.test();
        return ResponseEntity.ok().build();
    }
    @PostMapping(value = "/manager_request")
    public ResponseEntity<BloodSupplyDto> manager_request(@RequestBody BloodRequestDto input){
    	BloodSupplyDto dto = null;
		try {
			dto = this.bloodRequestService.handleManagerRequest(input);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	if(dto == null) {
    		return ResponseEntity.unprocessableEntity().build();
    	}
    	return ResponseEntity.ok(dto);
    }
}
