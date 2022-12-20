package com.example.isa.service.implementation;

import com.example.isa.model.Role;
import com.example.isa.repository.RoleRepository;
import com.example.isa.service.interfaces.RoleService;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class RoleServiceImpl implements RoleService {
    private final RoleRepository repository;

    public RoleServiceImpl(RoleRepository repository) {
        this.repository = repository;
    }

    @Override
    public Role findById(UUID id) {
        Optional<Role> role = repository.findById(id);
        return role.orElse(null);
    }

    @Override
    public Role findByName(String name) {
        return repository.findByNameIgnoreCase(name);
    }
}
