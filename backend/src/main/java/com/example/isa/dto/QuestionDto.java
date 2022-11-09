package com.example.isa.dto;

import com.example.isa.model.Question;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class QuestionDto {
    private UUID id;
    private String text;
    private String type;

    public QuestionDto(Question question) {
        this.id = question.getId();
        this.text = question.getText();
        this.type = question.getType().toString();
    }
}

