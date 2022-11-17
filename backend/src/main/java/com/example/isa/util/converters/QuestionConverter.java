package com.example.isa.util.converters;

import com.example.isa.dto.QuestionDto;
import com.example.isa.model.Question;
import org.springframework.stereotype.Service;

@Service
public class QuestionConverter implements Converter<Question, QuestionDto> {
    private final EnumConverter enumConverter;

    public QuestionConverter(EnumConverter enumConverter) {
        this.enumConverter = enumConverter;
    }

    @Override
    public QuestionDto entityToDto(Question question) {
        return QuestionDto.builder()
                .id(question.getId())
                .type(question.getType().toString())
                .text(question.getText())
                .build();
    }

    @Override
    public Question dtoToEntity(QuestionDto questionDto) {
        return Question.builder()
                .type(enumConverter.stringToQuestionType(questionDto.getType()))
                .text(questionDto.getText())
                .build();
    }
}
