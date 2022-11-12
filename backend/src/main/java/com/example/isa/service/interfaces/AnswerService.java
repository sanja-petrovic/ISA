package com.example.isa.service.interfaces;

import com.example.isa.model.Answer;
import com.example.isa.model.Patient;
import com.example.isa.model.User;

import java.util.List;

public interface AnswerService {
    List<Answer> getAll();
    void save(List<Answer> answers);
}
