package com.example.isa.controller;

import com.example.isa.dto.AnswerDto;
import com.example.isa.model.Answer;
import com.example.isa.service.interfaces.AnswerService;
import com.example.isa.service.interfaces.BloodDonorService;
import com.example.isa.service.interfaces.QuestionService;
import com.example.isa.util.converters.AnswerConverter;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/answers")
public class AnswerController {

    private final AnswerService answerService;
    private final QuestionService questionService;
    private final BloodDonorService bloodDonorService;
    private final AnswerConverter answerConverter;

    public AnswerController(AnswerService answerService, QuestionService questionService, BloodDonorService bloodDonorService, AnswerConverter answerConverter) {
        this.answerService = answerService;
        this.questionService = questionService;
        this.bloodDonorService = bloodDonorService;
        this.answerConverter = answerConverter;
    }

    @GetMapping
    @ApiOperation(value = "Get all answers.", httpMethod = "GET")
    public ResponseEntity<List<AnswerDto>> getAll() {
        List<Answer> answers = answerService.getAll();
        List<AnswerDto> answerDtos = answers.stream().map(answerConverter::entityToDto).collect(Collectors.toList());
        return ResponseEntity.ok(answerDtos);
    }

    @PutMapping
    @ApiOperation(value = "Save answers.", httpMethod = "PUT")
    @PreAuthorize("hasRole('ROLE_DONOR')")
    public ResponseEntity<?> save(@RequestBody List<AnswerDto> list) {
        List<Answer> answers = list.stream().map(dto -> Answer.builder().question(questionService.getById(dto.getQuestionId())).value(dto.isAnswerValue()).bloodDonor(bloodDonorService.getByEmail(dto.getUser())).build()).collect(Collectors.toList());
        answerService.save(answers);
        return ResponseEntity.ok().build();
    }
}
