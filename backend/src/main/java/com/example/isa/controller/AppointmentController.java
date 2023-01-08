package com.example.isa.controller;

import java.security.Principal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.example.isa.model.AppointmentStatus;
import com.example.isa.model.User;
import com.example.isa.service.interfaces.BloodDonorService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.example.isa.dto.AppointmentDto;
import com.example.isa.dto.AvailabilityDto;
import com.example.isa.dto.BloodBankDto;
import com.example.isa.exception.AlreadyExistsException;
import com.example.isa.exception.BloodBankClosedException;
import com.example.isa.model.Appointment;
import com.example.isa.model.BloodBank;
import com.example.isa.service.interfaces.AppointmentService;
import com.example.isa.util.converters.AppointmentConverter;
import com.example.isa.util.converters.BloodBankConverter;

@RestController
@RequestMapping("/appointments")
@Api(value = "/appointments", tags = "Appointments")
public class AppointmentController {
    private final AppointmentService appointmentService;
    private final BloodDonorService bloodDonorService;
    private final AppointmentConverter converter;
    private final BloodBankConverter bloodBankConverter;

    public AppointmentController(AppointmentService service, BloodDonorService bloodDonorService, AppointmentConverter converter, BloodBankConverter bloodBankConverter) {
        appointmentService = service;
        this.bloodDonorService = bloodDonorService;
        this.converter = converter;
        this.bloodBankConverter = bloodBankConverter;
    }

    @GetMapping
    @ApiOperation(value = "Get all appointments", httpMethod = "GET")
    public ResponseEntity<List<AppointmentDto>> getAll() {
        return ResponseEntity.ok(converter.listToDtoList(appointmentService.getAll()));
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Get an appointment by id", httpMethod = "GET")
    public ResponseEntity<AppointmentDto> getById(@PathVariable String id) {
        return ResponseEntity.ok(converter.entityToDto(appointmentService.getById(UUID.fromString(id))));
    }

    @GetMapping("/blood-bank/{id}")
    @PreAuthorize("hasRole('ROLE_STAFF') or hasRole('ROLE_ADMIN')")
    @ApiOperation(value = "Get all appointments in a blood bank.", httpMethod = "GET")
    public ResponseEntity<List<AppointmentDto>> getAllByBloodBank(@PathVariable String id) {
        return ResponseEntity.ok(converter.listToDtoList(appointmentService.getByBloodBank(UUID.fromString(id))));
    }

    @GetMapping("/blood-bank/{id}/available")
    @PreAuthorize("hasRole('ROLE_DONOR')")
    @ApiOperation(value = "Get all available appointments in a blood bank.", httpMethod = "GET")
    public ResponseEntity<List<AppointmentDto>> getAllUnscheduledByBloodBank(@PathVariable String id) {
        return ResponseEntity.ok(converter.listToDtoList(appointmentService.getUnscheduledByBloodBank(UUID.fromString(id))));
    }

    @GetMapping("/blood-donor/{id}")
    @PreAuthorize("hasRole('ROLE_DONOR')")
    @ApiOperation(value = "Get all appointments for a blood donor.", httpMethod = "GET")
    public ResponseEntity<List<AppointmentDto>> getAllByBloodDonor(@PathVariable String id) {
        return ResponseEntity.ok(converter.listToDtoList(appointmentService.getByBloodDonor(UUID.fromString(id))));
    }

    @PostMapping("/blood-donor/check-available")
    @PreAuthorize("hasRole('ROLE_DONOR')")
    @ApiOperation(value = "Check availability of banks for date.", httpMethod = "POST")
    public ResponseEntity<List<BloodBankDto>> checkAvailableForDate(@RequestBody AvailabilityDto dto) {
        Sort sort = Sort.by(Sort.Direction.fromString(dto.getSortCriteria().getDirection()), dto.getSortCriteria().getProperty());
        List<BloodBank> banks = null;
        try {
            banks = appointmentService.checkFreeBanksForDate(dto.getDateTime(), dto.getDuration(), sort);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        if (banks != null) {
            List<BloodBankDto> dtos = banks.stream().map(bloodBankConverter::entityToDto).toList();
            return ResponseEntity.ok(dtos);
        }

        return ResponseEntity.ok(new ArrayList<BloodBankDto>());
    }

    @GetMapping("/blood-donor")
    @PreAuthorize("hasRole('ROLE_DONOR')")
    @ApiOperation(value = "Get all appointments by the logged in blood donor.", httpMethod = "GET")
    public ResponseEntity<List<AppointmentDto>> getAllByLoggedInBloodDonor(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(converter.listToDtoList(appointmentService.getByBloodDonor(user.getId())));
    }

    @GetMapping("/blood-donor/upcoming")
    @PreAuthorize("hasRole('ROLE_DONOR')")
    @ApiOperation(value = "Get all upcoming appointments for a blood donor.", httpMethod = "GET")
    public ResponseEntity<List<AppointmentDto>> getAllUpcomingByBloodDonor(@AuthenticationPrincipal User user, @RequestParam(required = false, defaultValue = "DESC") String sortDirection, @RequestParam(required = false, defaultValue = "dateTime") String sortProperty) {
        Sort sort = Sort.by(Sort.Direction.fromString(sortDirection), sortProperty);
        return ResponseEntity.ok(converter.listToDtoList(appointmentService.getAllUpcomingByBloodDonor(user.getId(), sort)));
    }

    @GetMapping("/blood-donor/past")
    @PreAuthorize("hasRole('ROLE_DONOR')")
    @ApiOperation(value = "Get all past appointments for a blood donor.", httpMethod = "GET")
    public ResponseEntity<List<AppointmentDto>> getAllPastByBloodDonor(@AuthenticationPrincipal User user, @RequestParam(required = false, defaultValue = "DESC") String sortDirection, @RequestParam(required = false, defaultValue = "dateTime") String sortProperty) {
        Sort sort = Sort.by(Sort.Direction.fromString(sortDirection), sortProperty);
        return ResponseEntity.ok(converter.listToDtoList(appointmentService.getPastByBloodDonor(user.getId(), sort)));
    }

    @GetMapping("/blood-donor/cancelled")
    @PreAuthorize("hasRole('ROLE_DONOR')")
    @ApiOperation(value = "Get all cancelled appointments for a blood donor.", httpMethod = "GET")
    public ResponseEntity<List<AppointmentDto>> getAllCancelledByBloodDonor(@AuthenticationPrincipal User user, @RequestParam(required = false, defaultValue = "DESC") String sortDirection, @RequestParam(required = false, defaultValue = "dateTime") String sortProperty) {
        Sort sort = Sort.by(Sort.Direction.fromString(sortDirection), sortProperty);
        return ResponseEntity.ok(converter.listToDtoList(appointmentService.getByBloodDonor(user.getId(), AppointmentStatus.CANCELLED, sort)));
    }

    @PostMapping("/create")
    @ApiOperation(value = "Create an appointment.", httpMethod = "POST")
    @ResponseBody
    public ResponseEntity<String> createPredefined(@RequestBody AppointmentDto dto) {
        Appointment appointment = converter.dtoToEntity(dto);
        if (dto.getBloodDonorId() != null) {
            try {
                appointmentService.createByDonor(appointment, bloodDonorService.getByPersonalId(dto.getBloodDonorId()));
            } catch (AlreadyExistsException e) {
                return new ResponseEntity<>("AlreadyExistsException", HttpStatus.BAD_REQUEST);
            }
            return ResponseEntity.ok().build();
        } else {
            try {
                appointmentService.createScheduled(appointment);
            } catch (AlreadyExistsException e) {
                return new ResponseEntity<>("AlreadyExistsException", HttpStatus.BAD_REQUEST);
            } catch (BloodBankClosedException e) {
                return new ResponseEntity<>("BankClosed", HttpStatus.BAD_REQUEST);
            }
            return ResponseEntity.ok().build();
        }
    }

    @PostMapping("/schedule/{id}")
    @PreAuthorize("hasRole('ROLE_DONOR')")
    @ApiOperation(value = "Schedule one of the predefined appointments.", httpMethod = "POST")
    public ResponseEntity schedulePredefined(@PathVariable UUID id, Principal user) {
        appointmentService.schedulePredefined(appointmentService.getById(id), bloodDonorService.getByEmail(user.getName()));
        return ResponseEntity.ok().build();
    }

    @PostMapping("/cancel/{id}")
    @PreAuthorize("hasRole('ROLE_DONOR') or hasRole('ROLE_STAFF')")
    @ApiOperation(value = "Cancel a previously scheduled appointment.", httpMethod = "POST")
    public ResponseEntity cancel(@PathVariable UUID id, Principal user) {
        appointmentService.cancel(appointmentService.getById(id));
        return ResponseEntity.ok().build();
    }

    @PostMapping("/complete/{id}")
    @PreAuthorize("hasRole('ROLE_STAFF')")
    @ApiOperation(value = "Complete a previously scheduled appointment.", httpMethod = "POST")
    public ResponseEntity complete(@PathVariable UUID id, Principal user) {
        appointmentService.complete(appointmentService.getById(id));
        return ResponseEntity.ok().build();
    }

    @PostMapping("/schedule/{range}/{bankId}")
    @PreAuthorize("hasRole('ROLE_STAFF')")
    @ApiOperation(value = "Get schedule for time period", httpMethod = "POST")
    public ResponseEntity<List<AppointmentDto>> getScheduleByBloodBankInDateRange(@PathVariable String range, @PathVariable String bankId) {
        return ResponseEntity.ok(converter.listToDtoList(appointmentService.getScheduleByBloodBankInDateRange(bankId, range)));
    }

    @PostMapping("/missed/{id}")
    @PreAuthorize("hasRole('ROLE_STAFF')")
    @ApiOperation(value = "Set appointment as missed due to a donor not showing up.", httpMethod = "POST")
    public ResponseEntity setAsMissed(@PathVariable UUID id) {
        appointmentService.setStatus(appointmentService.getById(id), AppointmentStatus.MISSED);
        return ResponseEntity.ok().build();
    }


}
