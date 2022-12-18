package com.example.isa.service.implementation;

import com.example.isa.model.Admin;
import com.example.isa.model.Gender;
import com.example.isa.repository.AdminRepository;
import com.example.isa.service.interfaces.AdminService;
import com.example.isa.service.interfaces.RoleService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class AdminServiceImpl implements AdminService {
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;
    private final AdminRepository adminRepository;

    public AdminServiceImpl(RoleService roleService, PasswordEncoder passwordEncoder, AdminRepository adminRepository) {
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
        this.adminRepository = adminRepository;
    }

    @Override
    public void create(Admin admin) {
        admin.setRole(roleService.findByName("ADMIN"));
        adminRepository.save(admin);
    }

}
