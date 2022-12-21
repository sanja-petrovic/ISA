package com.example.isa.service.implementation;

import com.example.isa.exception.NotEnoughSupplyException;
import com.example.isa.model.BloodBank;
import com.example.isa.model.BloodSupply;
import com.example.isa.model.BloodType;
import com.example.isa.repository.BloodBankRepository;
import com.example.isa.service.interfaces.BloodBankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
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
    	String titleString;
    	String cityString;
    	if(searchCriteria == null) {
    		titleString = "";
    		cityString = "";
    	}
    	else {
    		 titleString = searchCriteria.get(0) == null ? "" : searchCriteria.get(0);
        	 cityString = searchCriteria.get(1) == null ? "" : searchCriteria.get(1);
    	}
    	
        if (titleString.equals("") && cityString.equals("")) {
        	return repository.findAllWithFilter(Double.parseDouble(filterGrade),sort);
        }
        else if(titleString.equals("")){
        	return repository.findByAddressCityLike("%"+cityString+"%",Double.parseDouble(filterGrade),sort);
        }
        else if(cityString.equals("")){
        	return repository.findByTitleLike("%"+titleString+"%",Double.parseDouble(filterGrade), sort);
        }
        else {
        	//return repository.findByTitleLikeAndAddressCityLike("%"+titleString+"%", "%"+cityString+"%",Double.parseDouble(filterGrade), sort);
        	 return this.repository.findAllByTitleIgnoreCaseContainingAndAddress_CityIgnoreCaseContainingAndAverageGradeGreaterThanEqual(titleString, cityString, parseGrade(filterGrade), sort);
        }
         
       
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


}
