package com.example.isa.repository;

import com.example.isa.model.MedicalStaff;
import com.example.isa.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface MedicalStaffRepository  extends JpaRepository<User, UUID> {

}
