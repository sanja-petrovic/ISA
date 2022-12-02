package com.example.isa.controller;

import com.example.isa.service.interfaces.BloodRequestService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
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
}
