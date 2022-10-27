package com.example.isa.service.implementation;

import com.example.isa.model.Question;
import com.example.isa.repository.QuestionRepository;
import com.example.isa.service.interfaces.QuestionService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionServiceImpl implements QuestionService {

    private final QuestionRepository repository;

    public QuestionServiceImpl(QuestionRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Question> getAll() {
        return repository.findAll();
    }

    @Override
    public void add(Question question) {
        repository.save(question);
    }

    @Override
    public Question getByText(String text) {
        return repository.findAllByText(text);
    }
}
