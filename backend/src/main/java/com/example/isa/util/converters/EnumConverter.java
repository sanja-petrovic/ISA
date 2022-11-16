package com.example.isa.util.converters;

import com.example.isa.model.Gender;
import com.example.isa.model.QuestionType;
import org.springframework.stereotype.Service;

@Service
public class EnumConverter {
    public Gender stringToGender(String input) {
        return input.trim().equalsIgnoreCase("female") ? Gender.FEMALE : Gender.MALE;
    }

    public QuestionType stringToQuestionType(String input) {
        return input.trim().equalsIgnoreCase("for_all") ? QuestionType.FOR_ALL : QuestionType.FOR_WOMEN;
    }
}
