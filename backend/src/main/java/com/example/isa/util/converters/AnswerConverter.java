package com.example.isa.util.converters;

import com.example.isa.dto.AnswerDto;
import com.example.isa.model.Answer;
import com.example.isa.service.interfaces.BloodDonorService;
import com.example.isa.service.interfaces.QuestionService;
import org.springframework.stereotype.Service;

@Service
public class AnswerConverter implements Converter<Answer, AnswerDto> {

    private final QuestionService questionService;
    private final BloodDonorService bloodDonorService;

    public AnswerConverter(QuestionService questionService, BloodDonorService bloodDonorService) {
        this.questionService = questionService;
        this.bloodDonorService = bloodDonorService;
    }

    @Override
    public AnswerDto entityToDto(Answer answer) {
        return AnswerDto.builder().
                questionId(answer.getQuestion().getId()).
                answerValue(answer.isValue()).
                user(answer.getUser().getEmail())
                .build();
    }

    @Override
    public Answer dtoToEntity(AnswerDto answerDto) {
        return Answer.builder()
                .question(questionService.getById(answerDto.getQuestionId()))
                .value(answerDto.isAnswerValue())
                .user(bloodDonorService.getByEmail(answerDto.getUser()))
                .build();
    }
}
