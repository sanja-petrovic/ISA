package com.example.isa.service.implementation;

import com.example.isa.exception.*;
import com.example.isa.model.Appointment;
import com.example.isa.model.AppointmentStatus;
import com.example.isa.model.BloodBank;
import com.example.isa.model.BloodDonor;
import com.example.isa.repository.AppointmentRepository;
import com.example.isa.service.interfaces.AppointmentService;
import com.example.isa.service.interfaces.BloodBankService;
import com.example.isa.util.converters.DateConverter;
import com.example.isa.util.email.EmailSender;
import com.example.isa.util.formatters.TextFormatter;
import com.example.isa.util.qrCode.QrCodeGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.awt.image.BufferedImage;
import java.text.ParseException;
import java.time.*;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Service
@Transactional(readOnly = true)
public class AppointmentServiceImpl implements AppointmentService {
    private final AppointmentRepository appointmentRepository;
    private final BloodBankService bloodBankService;
    private final EmailSender emailSender;

    @Autowired
    public AppointmentServiceImpl(AppointmentRepository appointmentRepository, BloodBankService bloodBankService, EmailSender emailSender) {
        this.appointmentRepository = appointmentRepository;
        this.bloodBankService = bloodBankService;
        this.emailSender = emailSender;
    }

    @Override
    public List<Appointment> getAll() {
        return appointmentRepository.findAll();
    }

    @Override
    public Appointment getById(UUID appointmentId) {
        return appointmentRepository.findById(appointmentId).orElse(null);
    }

    @Override
    public List<Appointment> getByBloodBank(UUID bloodBankId) {
        return appointmentRepository.findAllByBloodBankId(bloodBankId);
    }

    @Override
    public List<Appointment> getByBloodBank(String title) {
        return null;
    }

    @Override
    public List<Appointment> getUnscheduledByBloodBank(UUID bloodBankId) {
        Date today = new Date();
        return appointmentRepository.findAllByBloodBankIdAndStatus(bloodBankId, AppointmentStatus.NOT_SCHEDULED).stream().filter(appointment -> !today.after(appointment.getDateTime()) && !appointment.getDateTime().before(today)).toList();
    }

    @Override
    public List<Appointment> getByBloodDonor(UUID bloodDonorId) {
        return appointmentRepository.findAllByBloodDonorId(bloodDonorId);
    }

    @Override
    public List<Appointment> getByBloodDonor(UUID bloodDonorId, AppointmentStatus status, Sort sort) {
        return appointmentRepository.findAllByBloodDonorIdAndStatus(bloodDonorId, status, sort);
    }

    @Override
    public List<Appointment> getUpcomingScheduledByBloodDonor(UUID bloodDonorId, Sort sort) {
        return appointmentRepository.findAllByBloodDonorIdAndStatusAndDateTimeAfter(
                bloodDonorId,
                AppointmentStatus.SCHEDULED,
                Date.from(Instant.now().minus(Duration.ofDays(1))),
                sort);
    }

    public List<Appointment> getAllUpcomingByBloodDonor(UUID bloodDonorId, Sort sort) {
        return appointmentRepository.findAllByBloodDonorIdAndDateTimeAfter(
                bloodDonorId,
                Date.from(Instant.now().minus(Duration.ofDays(1))),
                sort);
    }


    public List<Appointment> getPastByBloodDonor(UUID bloodDonorId, Sort sort) {
        return appointmentRepository.findAllByBloodDonorIdAndDateTimeBefore(bloodDonorId, new Date(), sort);
    }

    @Override
    public BufferedImage generateQrCodeForAppointment(Appointment appointment) {
        String qrCodeInformation = TextFormatter.formatQrCodeInformation(appointment);
        try {
            return QrCodeGenerator.generateQRCodeImage(qrCodeInformation);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private boolean canScheduleAppointment(BloodDonor bloodDonor, Date date) {
        Optional<Appointment> mostRecentAppointment = appointmentRepository.findTopByBloodDonorOrderByDateTimeDesc(bloodDonor);
        LocalDateTime sixMonthsAgo = DateConverter.convert(date).minusMonths(6);
        Instant milliseconds = sixMonthsAgo.toInstant(ZoneOffset.UTC);
        return mostRecentAppointment.isEmpty() || mostRecentAppointment.get().getStatus() == AppointmentStatus.CANCELLED || !mostRecentAppointment.get().getDateTime().after(Date.from(milliseconds));
    }

    @Override
    @Transactional(readOnly = false)
    public Appointment create(Appointment appointment) {
        List<Appointment> listScheduled = appointmentRepository.findAllByBloodBankAndDateTime(appointment.getBloodBank(), appointment.getDateTime());
        for (Appointment scheduled : listScheduled) {
            if (this.hasOverlap(scheduled, appointment)) {
                throw new AlreadyExistsException();
            }
        }
        return appointmentRepository.save(appointment);
    }

    @Override
    @Transactional(readOnly = false)
    public Appointment createScheduled(Appointment appointment) {
        if (appointment != null) {
            if (!bloodBankService.isOpen(appointment.getBloodBank(),(Date) appointment.getDateTime().clone(), appointment.getDuration())) {
                throw new BloodBankClosedException();
            }
            LocalDateTime convertedDateTime = DateConverter.convert(appointment.getDateTime());
            List<Appointment> listScheduled = appointmentRepository.findAllByBloodBankAndDate(appointment.getBloodBank(), convertedDateTime.getYear(), convertedDateTime.getMonthValue(), convertedDateTime.getDayOfMonth());
            for (Appointment scheduled : listScheduled) {
                if (this.hasOverlap(scheduled, appointment)) {
                    throw new AlreadyExistsException();
                }
            }
            return appointmentRepository.save(appointment);
        }
        return null;
    }

    private boolean hasOverlap(Appointment first, Appointment second) {
        LocalDateTime firstLocal = DateConverter.convert(first.getDateTime());
        LocalDateTime secondLocal = DateConverter.convert(second.getDateTime());
        return (firstLocal.plusMinutes(first.getDuration()).isAfter(secondLocal) && firstLocal.isBefore(secondLocal.plusMinutes(second.getDuration())));
    }

    private boolean hasOverlap(Appointment appointment, Date dateTime, long duration) {
        LocalDateTime a1DateTime = DateConverter.convert(appointment.getDateTime());
        LocalDateTime a2DateTime = DateConverter.convert(dateTime);
        return (a1DateTime.plusMinutes(appointment.getDuration()).isAfter(a2DateTime) && a1DateTime.isBefore(a2DateTime.plusMinutes(duration)));
    }

    @Override
    @Transactional(readOnly = false)
    public Appointment save(Appointment appointment) {
        return appointmentRepository.save(appointment);
    }

    @Override
    @Transactional(readOnly = false)
    public void schedulePredefined(Appointment appointment, BloodDonor donor) {
        if (appointment != null) {
            if (appointment.getStatus() == AppointmentStatus.NOT_SCHEDULED) {
                if (appointment.getDateTime().before(new Date())) {
                    throw new PassedException();
                }
                if (CollectionUtils.isEmpty(donor.getAnswers())) {
                    throw new NoCompletedQuestionnaire();
                }
                if (donor.getPenaltyCount() >= 3) {
                    throw new ReachedPenaltyLimitException();
                }
                if (!canScheduleAppointment(donor, appointment.getDateTime())) {
                    throw new NewAppointmentTooSoonException();
                }
                if (appointmentRepository.findAllByBloodBankIdAndBloodDonorIdAndDateTime(appointment.getBloodBank().getId(), donor.getId(), appointment.getDateTime()).isPresent()) {
                    throw new CantScheduleTwiceException();
                }
                appointment.setBloodDonor(donor);
                appointment.setStatus(AppointmentStatus.SCHEDULED);
                appointmentRepository.save(appointment);
                try {
                    emailSender.sendAppointmentDetails(appointment);
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
    @Transactional(readOnly = false)
    public void cancel(Appointment appointment) {
        if (canCancelAppointment(appointment)) {
            appointment.setStatus(AppointmentStatus.CANCELLED);
            appointmentRepository.save(appointment);
            appointmentRepository.save(new Appointment(appointment));
        } else {
            throw new UnableToCancelException();
        }
    }

    @Override
    @Transactional(readOnly = false)
    public void setStatus(Appointment appointment, AppointmentStatus status) {
        appointment.setStatus(status);
        appointmentRepository.save(appointment);
    }

    @Override
    @Transactional(readOnly = false)
    public void complete(Appointment appointment) {
        appointment.setStatus(AppointmentStatus.COMPLETED);
        appointmentRepository.save(appointment);
    }

    @Override
    @Transactional(readOnly = false)
    public Appointment createByDonor(Appointment appointment, BloodDonor donor) {
        if (appointment != null && donor != null) {
            if (CollectionUtils.isEmpty(donor.getAnswers())) {
                throw new NoCompletedQuestionnaire();
            }
            if (donor.getPenaltyCount() >= 3) {
                throw new ReachedPenaltyLimitException();
            }
            if (appointment.getDateTime().before(new Date())) {
                throw new PassedException();
            }
            if (!bloodBankService.isOpen(appointment.getBloodBank(), (Date) appointment.getDateTime().clone(), appointment.getDuration())) {
                throw new BloodBankClosedException();
            }
            if (!canScheduleAppointment(donor, appointment.getDateTime())) {
                throw new NewAppointmentTooSoonException();
            }
            if (donorHasAtChosenTime(donor, appointment.getDateTime())) {
                throw new DuplicateAppointmentException();
            }
            appointment.setStatus(AppointmentStatus.SCHEDULED);
            appointment.setBloodDonor(donor);
            //create(appointment);
            List<Appointment> listScheduled = appointmentRepository.findAllByBloodBankAndDateTime(appointment.getBloodBank(), (Date) appointment.getDateTime().clone());
            for (Appointment scheduled : listScheduled) {
                if (this.hasOverlap(scheduled, appointment)) {
                	if(scheduled.getStatus().equals(AppointmentStatus.NOT_SCHEDULED)) {
                		scheduled.setStatus(AppointmentStatus.SCHEDULED);
                		scheduled.setBloodDonor(donor);
                		appointmentRepository.save(scheduled);
                		sendDetails(appointment);
                		return null;
                	}
                	else {
                		throw new AlreadyExistsException();
                	}  
                }
            }
            appointmentRepository.save(appointment);
            sendDetails(appointment);
        } else {
            throw new NotFoundException();
        }
        return null;
    }

	private void sendDetails(Appointment appointment) {
		try {
		    emailSender.sendAppointmentDetails(appointment);
		} catch (Exception e) {
		    e.printStackTrace();
		}
	}

    private boolean donorHasAtChosenTime(BloodDonor donor, Date dateTime) {
        return !appointmentRepository.findAllByBloodDonorAndDateTime(donor, dateTime).isEmpty();
    }

	@Override
    public List<BloodBank> getFreeBloodBanksForDate(String selectedDate, long duration, Sort sort) throws ParseException {
        List<BloodBank> openBloodBanksOnDate = bloodBankService.getAll().stream().filter(bloodBank -> bloodBankService.isOpen(bloodBank, DateConverter.convert(selectedDate), duration)).toList();
        List<BloodBank> freeBloodBanksForDate = new ArrayList<>();
        LocalDateTime localDateTime = DateConverter.convertToLocal(selectedDate);
        for (BloodBank bank : openBloodBanksOnDate) {
            List<Appointment> scheduledAppointments = appointmentRepository.findAllByBloodBankAndDateTime(bank, DateConverter.convert(localDateTime)).stream().filter(appointment -> !appointment.getStatus().equals(AppointmentStatus.NOT_SCHEDULED)).toList();
            if (scheduledAppointments.isEmpty()) {
                freeBloodBanksForDate.add(bank);
            } else {
                List<Appointment> overlappingAppointments = scheduledAppointments.stream().filter(appointment -> hasOverlap(appointment, DateConverter.convert(selectedDate), duration)).toList();
                if (overlappingAppointments.isEmpty()) {
                    freeBloodBanksForDate.add(bank);
                }
            }
        }
        return freeBloodBanksForDate;
    }

    @Override
    public List<Appointment> getScheduleByBloodBankInDateRange(String bankId, String range) {
        List<Appointment> bankSchedule = getByBloodBank(UUID.fromString(bankId));
        switch (range) {
            case "week":
                return getAppointmentsInDateRange(bankSchedule, LocalDate.now().plusDays(7));
            case "month":
                return getAppointmentsInDateRange(bankSchedule, LocalDate.now().plusMonths(1));
            case "year":
                return getAppointmentsInDateRange(bankSchedule, LocalDate.now().plusYears(1));
        }
        return null;
    }

	@Override
    public List<Appointment> getAppointmentsInDateRange(List<Appointment> appointments, LocalDate range) {
		return appointments.stream().filter(appointment -> appointment.getDateTime().before(Date.from(range.atStartOfDay(ZoneId.systemDefault()).toInstant()))).toList();
    }

    @Scheduled(cron = "@daily", zone = "Europe/Vienna")
    public void checkNotHeldAppointments() {
        for (Appointment appointment : appointmentRepository.findAllByStatus(AppointmentStatus.SCHEDULED)) {
            if (appointment.getDateTime().before(new Date())) {
                appointment.setStatus(AppointmentStatus.NOT_HELD);
            }
        }
    }
}
