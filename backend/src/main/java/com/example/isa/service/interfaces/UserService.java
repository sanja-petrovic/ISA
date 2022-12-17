package com.example.isa.service.interfaces;

import com.example.isa.model.MedicalStaff;
import com.example.isa.model.BloodDonor;
import com.example.isa.model.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {
    List<User> getAll();
    void register(BloodDonor bloodDonor);
    void verifyAccount(User user);
    public User findByUsername(String username);
    public User findByPersonalId(String personalId) throws Exception;
    public void updatePassword(User user, String newPassword);
    public void registerMedicalStaff(MedicalStaff medicalStaff);
    public List<User> search(String name, String lastName);
}
