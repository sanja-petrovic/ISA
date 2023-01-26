package com.example.isa.controller;
import com.example.isa.dto.AdminDto;
import com.example.isa.dto.AdminListDto;
import com.example.isa.model.Admin;
import com.example.isa.service.interfaces.AdminService;
import com.example.isa.service.interfaces.RoleService;
import com.example.isa.service.interfaces.UserService;
import com.example.isa.util.converters.AdminConverter;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import java.security.Principal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@RestController
@Api(value = "/admin", tags = "Admins")

@RequestMapping(value = "/admin")
public class AdminController {
    private final AdminService adminService;
    private final PasswordEncoder passwordEncoder;
    private final UserService userService;
    private final AdminConverter converter;
    private final RoleService roleService;

    @Autowired
    public AdminController(AdminService adminService, UserService userService, PasswordEncoder passwordEncoder, AdminConverter converter, RoleService roleService) {
        this.adminService = adminService;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.converter = converter;
        this.roleService = roleService;
    }

    @GetMapping
    @ApiOperation(value = "Get all admins.", httpMethod = "GET")
    public ResponseEntity<?> getAll() {
        List<Admin> admins = adminService.getAll();
        return ResponseEntity.ok(convertListToDto(admins));
    }
    @GetMapping("/current")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<AdminDto> getCurrentAdmin(Principal principal) {
        Optional<Admin> admin = adminService.findByEmail(principal.getName());
        return admin.map(admin1 -> ResponseEntity.ok(converter.entityToDto(admin1))).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping(value = "/findById/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Get an admin by id.", httpMethod = "GET")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> getAdminById(@PathVariable String id) {
        Admin admin = (Admin)userService.loadUserByUsername(id);
        if (admin == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(new AdminDto(admin));
    }
    @PostMapping(value ="/register")
    @ApiOperation(value = "Register an admin.", httpMethod = "POST")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> register(@RequestBody AdminDto adminDto){
        Admin admin = converter.dtoToEntity(adminDto);
        if (userService.findByUsername(adminDto.getEmail()) == null) {
            admin.setRole(roleService.findByName("ROLE_ADMIN"));
            adminService.register(admin);
            userService.registerAdmin(admin);
            return new ResponseEntity<>((admin), HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(null, HttpStatus.CONFLICT);
        }
    }

    private AdminListDto convertListToDto(List<Admin> adminsList) {
        List<AdminDto> adminsDtoList = adminsList.stream().map(converter::entityToDto).collect(Collectors.toList());
        return new AdminListDto(adminsDtoList);
    }
}
