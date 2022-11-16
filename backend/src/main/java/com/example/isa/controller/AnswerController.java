package com.example.isa.controller;

import com.example.isa.dto.AnswerDto;
import com.example.isa.model.Answer;
import com.example.isa.service.interfaces.AnswerService;
import com.example.isa.service.interfaces.PatientService;
import com.example.isa.service.interfaces.QuestionService;
import com.example.isa.util.Converters.AnswerConverter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/answers")
public class AnswerController {

    private final AnswerService answerService;
    private final QuestionService questionService;
    private final PatientService patientService;
    private final AnswerConverter answerConverter;

    public AnswerController(AnswerService answerService, QuestionService questionService, PatientService patientService, AnswerConverter answerConverter) {
        this.answerService = answerService;
        this.questionService = questionService;
        this.patientService = patientService;
        this.answerConverter = answerConverter;
    }

    @GetMapping
    public ResponseEntity<List<AnswerDto>> getAll() {
        List<Answer> answers = answerService.getAll();
        List<AnswerDto> answerDtos = answers.stream().map(answerConverter::entityToDto).collect(Collectors.toList());
        return ResponseEntity.ok(answerDtos);
    }

    @PutMapping
    public ResponseEntity<?> save(@RequestBody List<AnswerDto> list) {
        List<Answer> answers = list.stream().map(dto -> Answer.builder().question(questionService.getById(dto.getQuestionId())).value(dto.isAnswerValue()).user(patientService.getByEmail(dto.getUser())).build()).collect(Collectors.toList());
        answerService.save(answers);
        return ResponseEntity.ok().build();
    }
}
