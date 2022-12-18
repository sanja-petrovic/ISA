package com.example.isa.service.interfaces;

import com.example.isa.model.Answer;
import com.example.isa.model.BloodDonor;

import java.util.List;

public interface AnswerService {
    List<Answer> getAll();
    void save(List<Answer> answers);

    List<Answer> getByDonor(BloodDonor donor);
}
