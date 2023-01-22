package com.example.isa.service.implementation;

import com.example.isa.model.CacheData;
import com.example.isa.model.Question;
import com.example.isa.repository.CacheDataRepository;
import com.example.isa.repository.QuestionRepository;
import com.example.isa.service.interfaces.QuestionService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class QuestionServiceImpl implements QuestionService {

    private final QuestionRepository questionRepository;
    private final CacheDataRepository cacheDataRepository;
    private final ObjectMapper objectMapper;
    public QuestionServiceImpl(QuestionRepository questionRepository, CacheDataRepository cacheDataRepository, ObjectMapper objectMapper) {
        this.questionRepository = questionRepository;
        this.cacheDataRepository = cacheDataRepository;
        this.objectMapper = objectMapper;
    }

    @Override
    public List<Question> getAll() {
        return questionRepository.findAll();
    }

    @Override
    public void add(Question question) {
        questionRepository.save(question);
    }

    @Override
    public Question getByText(String text) {
        return questionRepository.findAllByText(text);
    }

    @Override
    public Question getById(UUID id) {
        return questionRepository.findAllById(id);
    }
}
