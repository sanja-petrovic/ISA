package com.example.isa.service.implementation;

import java.sql.SQLClientInfoException;
import java.sql.Time;
import java.time.*;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.example.isa.exception.*;
import com.example.isa.model.AppointmentStatus;
import com.example.isa.model.BloodBank;
import com.example.isa.model.BloodDonor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.metrics.buffering.StartupTimeline;
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
	public List<Appointment> getByBloodBank(String title) {
		return null;
	}

	@Override
	public List<Appointment> getUnscheduledByBloodBank(UUID bloodBankId) {
		Date today = new Date();
		return repository.findAllByBloodBankIdAndStatus(bloodBankId, AppointmentStatus.NOT_SCHEDULED).stream().filter(appointment -> !today.after(appointment.getDateTime()) && !appointment.getDateTime().before(today)).toList();
	}

	@Override
    public List<Appointment> getByBloodDonor(UUID bloodDonorId) {
        return repository.findAllByBloodDonorId(bloodDonorId);
    }
	@Override
	public List<Appointment> getUpcomingByBloodDonor(UUID bloodDonorId) {
		return repository.findAllByBloodDonorIdAndStatus(bloodDonorId, AppointmentStatus.SCHEDULED);
	}
    public boolean canScheduleAppointment(BloodDonor bloodDonor, Date date) {
        Optional<Appointment> mostRecentAppointment = repository.findTopByBloodDonorOrderByDateTimeDesc(bloodDonor);
		LocalDateTime sixMonthsAgo = DateConverter.convert(date).minusMonths(6);
		Instant milliseconds = sixMonthsAgo.toInstant(ZoneOffset.UTC);
        return mostRecentAppointment.isEmpty() || !mostRecentAppointment.get().getDateTime().after(Date.from(milliseconds));
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
    	if(appointment!=null) {
    		if(!this.bloodBankIsWorking(appointment)) {
				throw new BloodBankClosedException();
			}
    		LocalDateTime converteDateTime = DateConverter.convert(appointment.getDateTime());
            List<Appointment> listScheduled = repository.findAllByBloodBankAndDate(appointment.getBloodBank(), converteDateTime.getYear(), converteDateTime.getMonthValue(), converteDateTime.getDayOfMonth());
            for (Appointment scheduled : listScheduled) {
                if (this.hasDateTimeOverlap(scheduled, appointment)) {
                    throw new AlreadyExistsException();
                }
            }
            return repository.save(appointment);
    	}
    	return null;
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
				if (!canScheduleAppointment(donor, appointment.getDateTime())) {
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

	@Override
	public Appointment createByDonor(Appointment appointment, BloodDonor donor) {
		if(appointment!=null && donor!=null) {
			if (appointment.getDateTime().before(new Date())) {
	            throw new PassedException();
	        }
			if(!this.bloodBankIsWorking(appointment)) {
				throw new BloodBankClosedException();
			}
	        /*if (CollectionUtils.isEmpty(donor.getAnswers())) {
	            throw new NoCompletedQuestionnaire();
	        }
			/*if (!canScheduleAppointment(donor)) {
				throw new NewAppointmentTooSoonException();
			}*/
			//java.time.temporal.UnsupportedTemporalTypeException: Unsupported field: InstantSeconds
			//probbably missing parameter
			if(donorHasAtChosenTime(donor, appointment.getDateTime())) {
				throw new DuplicateAppointmentException();
			}
			appointment.setStatus(AppointmentStatus.SCHEDULED);
			appointment.setBloodDonor(donor);
			repository.save(appointment);
		}
		else {
			throw new NotFoundException();
		}
		return null;
	}
	private boolean donorHasAtChosenTime(BloodDonor donor, Date dateTime) {
		return !repository.findAllByBloodDonorAndDateTime(donor, dateTime).isEmpty();
	}
	private boolean bloodBankIsWorking(Appointment appointment) {
		LocalDateTime appointmentDateTime = DateConverter.convert(appointment.getDateTime());
		//appointmentDateTime = DateConverter.convert(appointment.getDateTime());
		LocalTime appointmentTime = appointmentDateTime.toLocalTime();
		Date appointmentDate = (Date) appointment.getDateTime().clone();
		BloodBank bank = appointment.getBloodBank();
		if(bank == null) return false;
		else {	
			Date startTime = appointmentDate;
			startTime.setTime(bank.getWorkingHours().getIntervalStart().getTime());
			LocalTime startTimeLocal = DateConverter.convert(startTime).toLocalTime();
			Date endTime = appointmentDate;
			endTime.setTime(bank.getWorkingHours().getIntervalEnd().getTime());
			LocalTime endTimeLocal = DateConverter.convert(endTime).toLocalTime();
			
			
			if(appointmentTime.isAfter(startTimeLocal) && appointmentTime.plusMinutes(appointment.getDuration()).isBefore(endTimeLocal)) return true;

		}
		return false;
	}
}
