package com.example.isa.controller;

import com.example.isa.dto.BloodRequestDto;
import com.example.isa.dto.BloodSupplyDto;
import com.example.isa.model.BloodRequest;
import com.example.isa.service.interfaces.BloodRequestService;

import java.text.ParseException;
import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Api(value = "/blood-requests", tags = "Blood requests")
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

    @GetMapping("/approved")
    @ApiOperation(value = "Get all blood approved requests.", httpMethod = "GET")
    public ResponseEntity<List<BloodRequestDto>> getAllApproved() {
        return ResponseEntity.ok(this.bloodRequestService.getAllApproved().stream().map(bloodRequest -> new BloodRequestDto(bloodRequest.getId(), bloodRequest.getBloodType().toString(), bloodRequest.getAmount(), bloodRequest.isUrgent(), bloodRequest.getSendOnDate() == null ? "" : bloodRequest.getSendOnDate().toString() )).toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<BloodRequestDto> getById(@PathVariable String id) {
        BloodRequest bloodRequest = bloodRequestService.getById(UUID.fromString(id));
        return ResponseEntity.ok(new BloodRequestDto(bloodRequest.getId(), bloodRequest.getBloodType().toString(), bloodRequest.getAmount(), bloodRequest.isUrgent(), bloodRequest.getSendOnDate().toString()));
    }
}
