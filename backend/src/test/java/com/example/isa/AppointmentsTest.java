package com.example.isa;

import com.example.isa.model.Appointment;
import com.example.isa.model.AppointmentStatus;
import com.example.isa.repository.AppointmentRepository;
import com.example.isa.repository.BloodDonorRepository;
import com.example.isa.service.interfaces.AppointmentService;
import com.example.isa.service.interfaces.BloodDonorService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class AppointmentsTest {
    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private AppointmentService appointmentService;
    @Autowired
    private BloodDonorService  bloodDonorService;

    @Test
    @Transactional
    public void concurrencyTest() {
        Date today = new Date();
        List<Appointment> appointmentsBefore = appointmentRepository.findAllByBloodBankIdAndStatus(UUID.fromString("16e4a8c2-3e86-4e93-825f-24e36cb29669"), AppointmentStatus.NOT_SCHEDULED).stream().filter(appointment -> !today.after(appointment.getDateTime()) && !appointment.getDateTime().before(today)).toList();
        Appointment appointment1 = appointmentsBefore.get(0);

        ExecutorService executor = Executors.newFixedThreadPool(2);
        try{
            Future future1 = executor.submit(() -> appointmentService.schedulePredefined(appointment1, bloodDonorService.getByEmail("blooddonor@gmail.com")));
            Future future2 = executor.submit(() -> appointmentService.schedulePredefined(appointment1, bloodDonorService.getByEmail("blooddonor2@gmail.com")));
            try {
                future1.get();
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
            try {
                future2.get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
            List<Appointment> appointmentsAfter = appointmentService.getUnscheduledByBloodBank(UUID.fromString("16e4a8c2-3e86-4e93-825f-24e36cb29669"));

            if(appointmentsBefore.size() - appointmentsAfter.size() == 2){
                assert false;
            }
            assert true;
        } catch (Exception e){
            assert false;
        }

    }
}
