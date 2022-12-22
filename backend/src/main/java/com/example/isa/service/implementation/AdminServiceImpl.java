package com.example.isa.service.implementation;

import com.example.isa.model.Admin;
import com.example.isa.model.User;
import com.example.isa.repository.AdminRepository;
import com.example.isa.service.interfaces.AdminService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class AdminServiceImpl implements AdminService {
    private  final AdminRepository repository;

    public AdminServiceImpl(AdminRepository repository) {
        this.repository = repository;
    }
    @Override
    public List<Admin> getAll() {
        return repository.findAll();
    }

    @Override
    public Admin getById(UUID id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public Admin updateAdmin(Admin admin) {
        if (admin.getPassword() == null || admin.getPassword().trim().isEmpty()) {
            //to do
        }
        return repository.save(admin);
    }

    @Override
    public boolean register(Admin admin) {
        repository.save(admin);
        return true;
    }

    @Override
    public Optional<Admin> findByEmail(String email) {
        return repository.findByEmail(email);
    }
    public Admin update(Admin admin) {

       return repository.save(admin);
    }

}
