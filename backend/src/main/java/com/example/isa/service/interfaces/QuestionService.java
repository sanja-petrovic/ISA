package com.example.isa.service.interfaces;

import com.example.isa.model.Question;

import java.util.List;

public interface QuestionService {

    List<Question> getAll();
    void add(Question question);
    Question getByText(String text);
}
