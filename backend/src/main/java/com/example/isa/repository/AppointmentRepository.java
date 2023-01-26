package com.example.isa.repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.example.isa.model.AppointmentStatus;
import com.example.isa.model.BloodBank;
import com.example.isa.model.BloodDonor;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.isa.model.Appointment;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, UUID> {
    List<Appointment> findAllByBloodDonorId(UUID bloodDonorId);
    List<Appointment> findAllByBloodBankId(UUID bloodBankId);
    List<Appointment> findAllByBloodBankIdAndStatus(UUID bloodBankId, AppointmentStatus status);
    List<Appointment> findAllByBloodDonorIdAndDateTimeBefore(UUID bloodDonorId, Date date, Sort sort);
    List<Appointment> findAllByBloodDonorIdAndDateTimeAfter(UUID bloodDonorId, Date date, Sort sort);
    List<Appointment> findAllByBloodDonorIdAndDateTimeAfter(UUID bloodDonorId, Date date);
    List<Appointment> findAllByBloodDonorIdAndStatusAndDateTimeAfter(UUID bloodDonorId, AppointmentStatus status, Date date, Sort sort);
    List<Appointment> findAllByBloodDonorIdAndStatusAndDateTimeAfter(UUID bloodDonorId, AppointmentStatus status, Date date);
    Optional<Appointment> findAllByBloodBankIdAndBloodDonorIdAndDateTime(UUID bloodBankId, UUID bloodDonorId, Date dateTime);
    Optional<Appointment> findTopByBloodDonorOrderByDateTimeDesc(BloodDonor bloodDonor);
    List<Appointment> findAllByBloodBankAndDateTime(BloodBank bloodBank, Date dateTime);
    List<Appointment> findAllByBloodDonorAndDateTime(BloodDonor bloodDonor, Date dateTime);
    @Query("from Appointment a where a.bloodBank = ?1 and year(a.dateTime) = ?2 and month(a.dateTime) = ?3 and day(a.dateTime) = ?4")
    List<Appointment> findAllByBloodBankAndDate(BloodBank bloodBank, int year, int month, int day);
    List<Appointment> findAllByBloodDonorIdAndStatus(UUID bloodDonorId, AppointmentStatus status, Sort sort);
    List<Appointment> findAllByStatus(AppointmentStatus status);

}
