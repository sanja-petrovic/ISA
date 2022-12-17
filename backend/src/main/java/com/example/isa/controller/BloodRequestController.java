package com.example.isa.controller;

import com.example.isa.dto.BloodRequestDto;
import com.example.isa.dto.BloodSupplyDto;
import com.example.isa.service.interfaces.BloodRequestService;

import java.text.ParseException;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(value = "/blood-requests")
@RequestMapping("/blood-requests")
public class BloodRequestController {

    private final BloodRequestService bloodRequestService;

    public BloodRequestController(BloodRequestService bloodRequestService) {
        this.bloodRequestService = bloodRequestService;
    }

    @PostMapping(value = "/manager")
    @ApiOperation(value = "Handle an incoming request from a hospital's manager", httpMethod = "POST")
    public ResponseEntity<BloodSupplyDto> handleManagerRequest(@RequestBody BloodRequestDto requestDto) throws ParseException, JsonProcessingException {
		BloodSupplyDto dto = bloodRequestService.handleManagerRequest(requestDto);
    	return dto == null ? ResponseEntity.unprocessableEntity().build() : ResponseEntity.ok(dto);
    }
}
