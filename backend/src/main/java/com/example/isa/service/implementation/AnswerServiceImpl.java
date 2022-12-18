package com.example.isa.service.implementation;

import com.example.isa.model.Answer;
import com.example.isa.model.BloodDonor;
import com.example.isa.repository.AnswerRepository;
import com.example.isa.service.interfaces.AnswerService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AnswerServiceImpl implements AnswerService {
    private final AnswerRepository repository;

    public AnswerServiceImpl(AnswerRepository repository) {
        this.repository = repository;
    }


    @Override
    public List<Answer> getAll() {
        return repository.findAll();
    }

    @Override
    public void save(List<Answer> answers) {
        repository.saveAll(answers);
    }

    @Override
    public List<Answer> getByDonor(BloodDonor donor) {
        return repository.findAllByBloodDonor(donor);
    }
}
