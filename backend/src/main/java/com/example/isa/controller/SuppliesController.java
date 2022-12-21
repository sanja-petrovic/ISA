package com.example.isa.controller;

import com.example.isa.dto.SuppliesDto;
import com.example.isa.model.Supplies;
import com.example.isa.repository.SuppliesRepository;
import com.example.isa.service.interfaces.SuppliesService;
import com.example.isa.util.converters.SuppliesConverter;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Api(value = "/supplies", tags = "Supplies")
@RequestMapping(value = "/supplies")
public class SuppliesController {
    private final SuppliesRepository suppliesRepository;
    private final SuppliesService suppliesService;
    private final SuppliesConverter suppliesConverter;

    @Autowired
    public  SuppliesController(SuppliesRepository suppliesRepository, SuppliesService suppliesService,
                               SuppliesConverter suppliesConverter){
        this.suppliesRepository = suppliesRepository;
        this.suppliesService = suppliesService;
        this.suppliesConverter = suppliesConverter;
    }

    @GetMapping
    @ApiOperation(value = "Get all supplies", httpMethod = "GET")
    public ResponseEntity<List<SuppliesDto>> getAll(){
        List<Supplies> suppliesList = suppliesService.getAll();
        List<SuppliesDto> suppliesDtoList = suppliesList.stream().map(suppliesConverter::entityToDto).toList();
        return ResponseEntity.ok(suppliesDtoList);
    }

    @PutMapping(value = "/update")
    @PreAuthorize("hasRole('ROLE_STAFF')")
    @ApiOperation(value = "Update supplies", httpMethod = "PUT")
    public ResponseEntity<SuppliesDto> update(@RequestBody SuppliesDto suppliesDto){
        Supplies retVal = suppliesService.update(suppliesConverter.dtoToEntity(suppliesDto));
        return retVal == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(suppliesConverter.entityToDto(retVal));
    }
}
