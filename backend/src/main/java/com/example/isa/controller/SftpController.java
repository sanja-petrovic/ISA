package com.example.isa.controller;

import com.example.isa.service.interfaces.SftpService;
import io.swagger.annotations.Api;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(value = "/sftp", tags = "Sftp")
@RequestMapping(value = "/sftp")
public class SftpController {
    private final SftpService service;

    public SftpController(SftpService service) {
        this.service = service;
    }

    @GetMapping("/download/{filePath}")
    public ResponseEntity download(@PathVariable String filePath) {
        try {
            service.download(filePath);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
        return ResponseEntity.ok().build();
    }
}
