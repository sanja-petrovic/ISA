package com.example.isa.util;

import com.example.isa.model.Gender;
import org.springframework.stereotype.Service;

@Service
public class EnumConverter {

    public Gender StringToGender(String input) {
        return input.trim().equalsIgnoreCase("female") ? Gender.FEMALE : Gender.MALE;
    }
}
