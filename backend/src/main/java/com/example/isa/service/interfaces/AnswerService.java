package com.example.isa.service.interfaces;

import com.example.isa.model.Answer;

import java.util.List;

public interface AnswerService {
    List<Answer> getAll();
    void save(List<Answer> answers);
}
