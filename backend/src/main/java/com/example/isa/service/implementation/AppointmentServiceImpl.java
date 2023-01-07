package com.example.isa.service.implementation;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.time.*;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.TimeZone;
import java.util.UUID;

import com.example.isa.exception.*;
import com.example.isa.model.AppointmentStatus;
import com.example.isa.model.BloodBank;
import com.example.isa.model.BloodDonor;
import com.example.isa.model.Email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.example.isa.model.Appointment;
import com.example.isa.repository.AppointmentRepository;
import com.example.isa.service.interfaces.AppointmentService;
import com.example.isa.service.interfaces.BloodBankService;
import com.example.isa.util.EmailSender;
import com.example.isa.util.QrCodeGenerator;
import com.example.isa.util.converters.DateConverter;

import org.springframework.data.domain.Sort;

import org.springframework.util.CollectionUtils;

import javax.imageio.ImageIO;
import javax.transaction.Transactional;

@Service
public class AppointmentServiceImpl implements AppointmentService {
    private final AppointmentRepository repository;
    private final BloodBankService bankService;
    private final EmailSender mailSender;

    @Autowired
    public AppointmentServiceImpl(AppointmentRepository repository, BloodBankService bankService,EmailSender mailSender) {
        this.repository = repository;
        this.bankService = bankService;
        this.mailSender = mailSender;
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
	public List<Appointment> getByBloodDonor(UUID bloodDonorId, AppointmentStatus status) {
		return repository.findAllByBloodDonorIdAndStatus(bloodDonorId, status);
	}

	@Override
	public List<Appointment> getUpcomingByBloodDonor(UUID bloodDonorId) {
		return repository.findAllByBloodDonorIdAndStatus(bloodDonorId, AppointmentStatus.SCHEDULED);
	}
    public boolean canScheduleAppointment(BloodDonor bloodDonor, Date date) {
        Optional<Appointment> mostRecentAppointment = repository.findTopByBloodDonorOrderByDateTimeDesc(bloodDonor);
		LocalDateTime sixMonthsAgo = DateConverter.convert(date).minusMonths(6);
		Instant milliseconds = sixMonthsAgo.toInstant(ZoneOffset.UTC);
        return mostRecentAppointment.isEmpty() || mostRecentAppointment.get().getStatus() == AppointmentStatus.CANCELLED || !mostRecentAppointment.get().getDateTime().after(Date.from(milliseconds));
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
    private boolean hasDateTimeOverlap(Appointment a1, Date DateTime, long duration) {
    	LocalDateTime a1DateTime = DateConverter.convert(a1.getDateTime());
    	LocalDateTime a2DateTime = DateConverter.convert(DateTime);
    	//A.end >= B.start AND A.start <= B.end
        return (a1DateTime.plusMinutes(a1.getDuration()).isAfter(a2DateTime) && a1DateTime.isBefore(a2DateTime.plusMinutes(duration)));
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
				if(donor.getPenaltyCount() >= 3) {
					throw new ReachedPenaltyLimitException();
				}
				if (!canScheduleAppointment(donor, appointment.getDateTime())) {
					throw new NewAppointmentTooSoonException();
				}
                if (repository.findAllByBloodBankIdAndBloodDonorIdAndDateTime(appointment.getBloodBank().getId(), donor.getId(), appointment.getDateTime()).isPresent()) {
                    throw new CantScheduleTwiceException();
                }
				appointment.setBloodDonor(donor);
                appointment.setStatus(AppointmentStatus.SCHEDULED);
				repository.save(appointment);
				try {
					sendDetails(appointment, donor);
				} catch (Exception e) {
					throw new RuntimeException(e);
				}
			} else {
                throw new AlreadyScheduledException();
            }
        } else {
            throw new NotFoundException();
        }
    }

	public boolean canCancelAppointment(Appointment appointment) {
		LocalDateTime appointmentDateTime = DateConverter.convert(appointment.getDateTime());
		long hours = ChronoUnit.HOURS.between(appointmentDateTime, LocalDateTime.now());
		return Math.abs(hours) > 24;
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
	@Transactional
	public void complete(Appointment appointment) {
		appointment.setStatus(AppointmentStatus.COMPLETED);
		repository.save(appointment);
	}

	@Override
	public Appointment createByDonor(Appointment appointment, BloodDonor donor) {
		if(appointment!=null && donor!=null) {
			if (CollectionUtils.isEmpty(donor.getAnswers())) {
				throw new NoCompletedQuestionnaire();
			}
			if(donor.getPenaltyCount() >= 3) {
				throw new ReachedPenaltyLimitException();
			}
			if (appointment.getDateTime().before(new Date())) {
	            throw new PassedException();
	        }
			if(!this.bloodBankIsWorking(appointment)) {
				throw new BloodBankClosedException();
			}
			if (!canScheduleAppointment(donor, appointment.getDateTime())) {
				throw new NewAppointmentTooSoonException();
			}
			if(donorHasAtChosenTime(donor, appointment.getDateTime())) {
				throw new DuplicateAppointmentException();
			}
			appointment.setStatus(AppointmentStatus.SCHEDULED);
			appointment.setBloodDonor(donor);
			repository.save(appointment);
			try {
				sendDetails(appointment, donor);
			} catch (IOException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		else {
			throw new NotFoundException();
		}
		return null;
	}
	@Async
	void sendDetails(Appointment appointment, BloodDonor donor) throws IOException, Exception {
		StringBuilder mailBodyBuilder = new StringBuilder();
		mailBodyBuilder.append("Your appointment at bank: ");
		mailBodyBuilder.append(appointment.getBloodBank().getTitle());
		mailBodyBuilder.append(" has been successfully scheduled for ");
		mailBodyBuilder.append(appointment.getDateTime().toString());
		
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		ImageIO.write(QrCodeGenerator.generateQRCodeImage(mailBodyBuilder.toString()), "png", byteArrayOutputStream);
		byteArrayOutputStream.flush();
		byte[] imageBytes= byteArrayOutputStream.toByteArray();
		byteArrayOutputStream.close();
		
		mailSender.sendWithImage(new Email(donor.getEmail(),"Appointment scheduled", mailBodyBuilder.toString()),imageBytes);
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
			
			if(startTimeLocal.equals(LocalTime.of(0,0, 0)) && endTimeLocal.equals(LocalTime.of(0,0, 0))) return true;
			
			if(appointmentTime.isAfter(startTimeLocal) && appointmentTime.plusMinutes(appointment.getDuration()).isBefore(endTimeLocal)) return true;

		}
		return false;
	}
	
	private boolean bloodBankIsWorking(BloodBank bank, Date date, long duration) {
		if(bank == null) return false;
		else {
			LocalDateTime appointmentDateTime = DateConverter.convert(date);
			LocalTime appointmentTime = appointmentDateTime.toLocalTime();
			Date appointmentDate = date;
			Date startTime = appointmentDate;
			startTime.setTime(bank.getWorkingHours().getIntervalStart().getTime());
			LocalTime startTimeLocal = DateConverter.convert(startTime).toLocalTime();
			Date endTime = appointmentDate;
			endTime.setTime(bank.getWorkingHours().getIntervalEnd().getTime());
			LocalTime endTimeLocal = DateConverter.convert(endTime).toLocalTime();
			
			if(startTimeLocal.equals(LocalTime.of(0,0, 0)) && endTimeLocal.equals(LocalTime.of(0,0, 0))) return true;
			
			if(appointmentTime.isAfter(startTimeLocal) && appointmentTime.plusMinutes(duration).isBefore(endTimeLocal)) return true;

		}
		return false;
	}
	
	public List<BloodBank> checkFreeBanksForDate(String dateTimeString, long duration, Sort sort) throws ParseException{
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.ENGLISH);
		formatter.setTimeZone(TimeZone.getDefault());
		Date dateTime = formatter.parse(dateTimeString);
		List<String> searchCriteria = new ArrayList<String>();
		searchCriteria.add("");
		searchCriteria.add("");
		List<BloodBank> banks = bankService.search(sort, searchCriteria, "0");
		if(banks!=null) {
			List<BloodBank> retVal = new ArrayList<BloodBank>();
			LocalDateTime convertedDateTime = DateConverter.convert(dateTime);
			for (BloodBank bank : banks) {
				if(!this.bloodBankIsWorking(bank,(Date)dateTime.clone(), duration)) {
					continue;
				}
				List<Appointment> listScheduled = repository.findAllByBloodBankAndDate( bank, convertedDateTime.getYear(), convertedDateTime.getMonthValue(), convertedDateTime.getDayOfMonth());
				if(listScheduled == null) {
					retVal.add(bank);
					continue;
				}
				else {
					boolean overlap = false;
					for(Appointment appointment : listScheduled) {
						if(hasDateTimeOverlap(appointment, dateTime, duration)){
							overlap = true;
							break;
						}
					}
					if(overlap==false) {
						retVal.add(bank);
					}
				}
			}
			return retVal.isEmpty() ? null : retVal;
		}
		else {
			return null;
		}
	}

	@Override
	public List<Appointment> getScheduleByBloodBankInDateRange(String bankId, String range) {
		List<Appointment> bankSchedule = getByBloodBank(UUID.fromString(bankId));
		switch (range){
			case "week" :
				return getAppointmentsInDateRange(bankSchedule,LocalDate.now().plusDays(7));
			case "month":
				return getAppointmentsInDateRange(bankSchedule,LocalDate.now().plusMonths(1));
			case "year":
				return getAppointmentsInDateRange(bankSchedule,LocalDate.now().plusYears(1));
		}
		return null;
	}
	public List<Appointment> getAppointmentsInDateRange(List<Appointment> appointments, LocalDate range){
		List<Appointment> schedule = new ArrayList<Appointment>();
		for(Appointment appointment : appointments){
			if(appointment.getDateTime().before(Date.from(range.atStartOfDay(ZoneId.systemDefault()).toInstant()))){
				schedule.add(appointment);
			}
		}
		return schedule;
	}
}
