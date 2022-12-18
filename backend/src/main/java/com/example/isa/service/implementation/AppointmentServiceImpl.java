package com.example.isa.service.implementation;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.example.isa.exception.*;
import com.example.isa.model.AppointmentStatus;
import com.example.isa.model.BloodDonor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.isa.model.Appointment;
import com.example.isa.repository.AppointmentRepository;
import com.example.isa.service.interfaces.AppointmentService;
import com.example.isa.util.converters.DateConverter;
import org.springframework.util.CollectionUtils;

import javax.transaction.Transactional;

@Service
public class AppointmentServiceImpl implements AppointmentService {
    private final AppointmentRepository repository;

    @Autowired
    public AppointmentServiceImpl(AppointmentRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Appointment> getAll() {
        return repository.findAll();
    }

	@Override
	public Appointment getById(UUID appointmentId) {
		return repository.findById(appointmentId).orElse(null);
	}

	@Override
    public List<Appointment> getByBloodBank(UUID bloodBankId) {
        return repository.findAllByBloodBankId(bloodBankId);
    }

    @Override
    public List<Appointment> getByBloodDonor(UUID bloodDonorId) {
        return repository.findAllByBloodDonorId(bloodDonorId);
    }

    public boolean canScheduleAppointment(BloodDonor bloodDonor) {
        Optional<Appointment> mostRecentAppointment = repository.findTopByBloodDonorOrderByDateTimeDesc(bloodDonor);
        return mostRecentAppointment.isEmpty() || mostRecentAppointment.get().getDateTime().after(Date.from(Instant.from(LocalDate.now().minusMonths(6))));
    }

    @Override
    @Transactional
    public Appointment create(Appointment appointment) {
        List<Appointment> listScheduled = repository.findAllByBloodBankAndDateTime(appointment.getBloodBank(), appointment.getDateTime());
        for (Appointment scheduled : listScheduled) {
            if (this.hasDateTimeOverlap(scheduled, appointment)) {
                throw new AlreadyExistsException();
            }
        }
        return repository.save(appointment);
    }
    @Override
    @Transactional
    public Appointment createScheduled(Appointment appointment) {
    	LocalDateTime converteDateTime = DateConverter.convert(appointment.getDateTime());
        List<Appointment> listScheduled = repository.findAllByBloodBankAndDate(appointment.getBloodBank(), converteDateTime.getYear(), converteDateTime.getMonthValue(), converteDateTime.getDayOfMonth());
        for (Appointment scheduled : listScheduled) {
            if (this.hasDateTimeOverlap(scheduled, appointment)) {
                throw new AlreadyExistsException();
            }
        }
        return repository.save(appointment);
    }

    
    private boolean hasDateTimeOverlap(Appointment a1, Appointment a2) {
    	LocalDateTime a1DateTime = DateConverter.convert(a1.getDateTime());
    	LocalDateTime a2DateTime = DateConverter.convert(a2.getDateTime());
    	//A.end >= B.start AND A.start <= B.end
        return (a1DateTime.plusMinutes(a1.getDuration()).isAfter(a2DateTime) && a1DateTime.isBefore(a2DateTime.plusMinutes(a2.getDuration())));
    }
    @Override
    public Appointment update(Appointment appointment) {
        return repository.save(appointment);
    }



    @Override
    @Transactional
    public void schedulePredefined(Appointment appointment, BloodDonor donor) {
        if (appointment != null) {
            if (appointment.getStatus() == AppointmentStatus.NOT_SCHEDULED) {
                if (appointment.getDateTime().before(new Date())) {
                    throw new PassedException();
                }
                if (CollectionUtils.isEmpty(donor.getAnswers())) {
                    throw new NoCompletedQuestionnaire();
                }
				if (!canScheduleAppointment(donor)) {
					throw new NewAppointmentTooSoonException();
				}
                if (repository.findAllByBloodBankAndBloodDonorAndDateTime(appointment.getBloodBank(), donor, appointment.getDateTime()).isPresent()) {
                    throw new CantScheduleTwiceException();
                }
				appointment.setBloodDonor(donor);
                appointment.setStatus(AppointmentStatus.SCHEDULED);
				repository.save(appointment);
            } else {
                throw new AlreadyScheduledException();
            }
        } else {
            throw new NotFoundException();
        }
    }

	public boolean canCancelAppointment(Appointment appointment) {
		return Duration.between(LocalDate.now(), LocalDate.from(appointment.getDateTime().toInstant())).toHours() > 24;
	}

	@Override
	@Transactional
	public void cancel(Appointment appointment) {
		if(canCancelAppointment(appointment)) {
			appointment.setStatus(AppointmentStatus.CANCELLED);
			repository.save(appointment);
            repository.save(new Appointment(appointment));
		} else {
			throw new UnableToCancelException();
		}
	}


}
