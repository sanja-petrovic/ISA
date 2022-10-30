package com.example.isa.controller;

import com.example.isa.dto.BloodBankDto;
import com.example.isa.dto.BloodBankListDto;
import com.example.isa.model.BloodBank;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@Api(value = "/blood-supply/")
@RequestMapping(value = "/blood-supply/")
public class BloodSupplyController {


    @Autowired
    public BloodSupplyController() {
    }

    @GetMapping
    @ApiOperation(value = "Check if blood bank has certain supply.", httpMethod = "GET")
    public ResponseEntity<Boolean> getAll(String bloodType, int quantity) {
        if(bloodType.equals("0+") && quantity > 3) {
            return ResponseEntity.ok(false);
        }
        if(bloodType.equals("0-") && quantity > 2) {
            return ResponseEntity.ok(false);
        }
        if(bloodType.equals("A+")) {
            return ResponseEntity.ok(false);
        }
        return ResponseEntity.ok(true);
    }
}
