package com.example.isa.service.implementation;

import com.example.isa.exception.NotEnoughSupplyException;
import com.example.isa.model.BloodBank;
import com.example.isa.model.BloodSupply;
import com.example.isa.model.BloodType;
import com.example.isa.repository.BloodBankRepository;
import com.example.isa.service.interfaces.BloodBankService;
import com.example.isa.util.converters.DateConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class BloodBankServiceImpl implements BloodBankService {
    private final BloodBankRepository repository;

    @Autowired
    public BloodBankServiceImpl(BloodBankRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<BloodBank> getAll() {
        return repository.findAll();
    }

    @Override
    public BloodBank getById(UUID id) {
        return repository.findById(id).orElse(null);

    }

    @Override
    public List<BloodBank> search(Sort sort, List<String> searchCriteria, String filterGrade) {
        String titleString = searchCriteria.get(0) == null ? "" : searchCriteria.get(0);
        String cityString = searchCriteria.get(1) == null ? "" : searchCriteria.get(1);

        return this.repository.findAllByTitleIgnoreCaseContainingAndAddress_CityIgnoreCaseContainingAndAverageGradeGreaterThanEqual(titleString, cityString, parseGrade(filterGrade), sort);
       
    }

    @Override
    public List<BloodBank> getAll(Sort sort) {
        return this.repository.findAll(sort);
    }

    private Double parseGrade(String filterGrade) {
        try {
            return Double.parseDouble(filterGrade);
        } catch (NumberFormatException e) {
            return 0.0;
        }
    }
    @Override
    public BloodBank update(BloodBank bloodBank) {
        if (bloodBank.getTitle() == null || bloodBank.getAddress() == null) {
            return null;
        }
        return repository.save(bloodBank);
    }

    @Override
    public void register(BloodBank bank) {
        repository.save(bank);
    }

    @Override
    public BloodBank findByTitle(String title) {
        return repository.findAllByTitleIgnoreCase(title).orElse(null);
    }

    public BloodBank findBankWithMostSupplies(BloodType type, Double amount) {
        BloodBank bankWithMostBloodOfType = null;
        double amountOfBloodOfType = 0;
        for(BloodBank bloodBank : repository.findAll()) {
            for(BloodSupply bloodSupply : bloodBank.getBloodSupplies()) {
                if(bloodSupply.getType().equals(type) && bloodSupply.getAmount() - amount >= 0) {
                    if(bloodSupply.getAmount() > amountOfBloodOfType) {
                        bankWithMostBloodOfType = bloodBank;
                        amountOfBloodOfType = bloodSupply.getAmount();
                    }
                }
            }
        }

        return bankWithMostBloodOfType;
    }

    @Override
    public boolean checkBloodSupply(BloodBank bloodBank, BloodType type, Double amount) {
        for(BloodSupply bloodSupply : bloodBank.getBloodSupplies()) {
            if(bloodSupply.getType().equals(type) && bloodSupply.getAmount() - amount >= 0) {
                return true;
            }
        }
        return false;
    }

    @Override
    @Transactional
    public void updateBloodSupply(BloodBank bloodBank, BloodType type, Double amount) {
        for(BloodSupply bloodSupply : bloodBank.getBloodSupplies()) {
            if(bloodSupply.getType().equals(type) && bloodSupply.getAmount() - amount >= 0) {
                bloodSupply.setAmount(bloodSupply.getAmount() - amount);
            } else {
                throw new NotEnoughSupplyException();
            }
        }
    }

    @Override
    public boolean isOpen(BloodBank bank, Date date, long duration) {
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

        return appointmentTime.isAfter(startTimeLocal) && appointmentTime.plusMinutes(duration).isBefore(endTimeLocal);
    }
}
