package com.example.isa.service.interfaces;

import com.example.isa.model.Admin;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AdminService {
    List<Admin> getAll();
    Admin getById(UUID id);

    Admin updateAdmin(Admin admin);
    boolean register(Admin admin);
    Optional<Admin> findByEmail(String email);

}
