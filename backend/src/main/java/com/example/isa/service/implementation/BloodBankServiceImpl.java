package com.example.isa.service.implementation;

import com.example.isa.model.BloodBank;
import com.example.isa.repository.BloodBankRepository;
import com.example.isa.service.interfaces.BloodBankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

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
    public List<BloodBank> search(Sort sort, List<String> searchCriteria) {
        return null;
    }

    @Override
    public List<BloodBank> search(Sort sort, List<String> searchCriteria, String filterGrade) {
    	String titleString = searchCriteria.get(0);
    	String cityString = searchCriteria.get(1);
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
        	return repository.findByTitleLikeAndAddressCityLike("%"+titleString+"%", "%"+cityString+"%",Double.parseDouble(filterGrade), sort);
        }
    }

    @Override
    public BloodBank updateBloodBank(BloodBank bloodBank) {
        if (bloodBank.getTitle() == null || bloodBank.getAddress() == null) {
            return null;
        }
        return repository.save(bloodBank);
    }
<<<<<<< HEAD
    @Override
    public boolean registerBank (BloodBank bank){
         repository.save(bank);
         return true;
=======

    @Override
    public BloodBank findByTitle(String title) {
        return repository.findAllByTitleIgnoreCase(title).orElse(null);
>>>>>>> development
    }
}
