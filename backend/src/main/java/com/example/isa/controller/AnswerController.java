package com.example.isa.controller;

import com.example.isa.dto.AnswerDto;
import com.example.isa.dto.AnswerListDto;
import com.example.isa.model.Answer;
import com.example.isa.service.interfaces.AnswerService;
import com.example.isa.service.interfaces.PatientService;
import com.example.isa.service.interfaces.QuestionService;
import com.example.isa.service.interfaces.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/answers")
public class AnswerController {

    private final AnswerService answerService;
    private final QuestionService questionService;
    private final PatientService patientService;

    public AnswerController(AnswerService answerService, QuestionService questionService, PatientService patientService) {
        this.answerService = answerService;
        this.questionService = questionService;
        this.patientService = patientService;
    }

    @GetMapping
    public ResponseEntity<AnswerListDto> getAll() {
        List<Answer> answers = answerService.getAll();
        List<AnswerDto> answerDtos = answers.stream().map(answer -> AnswerDto.builder().questionText(answer.getQuestion().getText()).answerValue(answer.isValue()).user(answer.getUser().getEmail()).build()).collect(Collectors.toList());
        AnswerListDto listDto = new AnswerListDto(answerDtos);
        return ResponseEntity.ok(listDto);
    }

    @PostMapping
    public ResponseEntity<?> save(AnswerListDto list) {
        List<Answer> answers = list.getAnswers().stream().map(dto -> Answer.builder().question(questionService.getByText(dto.getQuestionText())).value(dto.isAnswerValue()).user(patientService.getByEmail(dto.getUser())).build()).collect(Collectors.toList());
        answerService.save(answers);
        return ResponseEntity.ok().build();
    }
}
