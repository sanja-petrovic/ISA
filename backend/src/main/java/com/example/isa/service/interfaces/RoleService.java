package com.example.isa.service.interfaces;

import com.example.isa.model.Role;

import java.util.List;
import java.util.UUID;

public interface RoleService {
    Role findById(UUID id);
    List<Role> findByName(String name);
}
