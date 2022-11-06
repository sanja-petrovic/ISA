package com.example.isa.service.interfaces;

import com.example.isa.model.Role;

import java.util.List;
import java.util.UUID;

public interface RoleService {
    public Role findById(UUID id);
    public List<Role> findByName(String name);
}
