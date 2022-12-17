package com.example.isa.repository;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import com.example.isa.model.BloodBank;
import com.example.isa.model.BloodDonor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.isa.model.Appointment;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, UUID> {
    List<Appointment> findAllByBloodDonor(UUID bloodDonorId);

    List<Appointment> findAllByBloodBank(UUID bloodBankId);

    List<Appointment> findAllByDateTime(Date date);

    List<Appointment> findAllByBloodBankAndDateTime(BloodBank bloodBank, Date dateTime);


}
