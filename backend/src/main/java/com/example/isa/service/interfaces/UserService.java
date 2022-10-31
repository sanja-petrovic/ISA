package com.example.isa.service.interfaces;

import com.example.isa.model.Patient;
import com.example.isa.model.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {
    List<User> getAll();
    void register(Patient patient);
}
