package com.example.isa.controller;

import com.example.isa.dto.QuestionDto;
import com.example.isa.model.Question;
import com.example.isa.service.interfaces.QuestionService;
import com.example.isa.util.converters.QuestionConverter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/questions")
public class QuestionController {

    private final QuestionService service;
    private final QuestionConverter questionConverter;

    public QuestionController(QuestionService service, QuestionConverter questionConverter) {
        this.service = service;
        this.questionConverter = questionConverter;
    }

    @GetMapping
    public ResponseEntity<List<QuestionDto>> getAll() {
        List<Question> questions = service.getAll();
        List<QuestionDto> questionDtos = questions.stream().map(questionConverter::entityToDto).collect(Collectors.toList());
        return ResponseEntity.ok(questionDtos);
    }

    @PostMapping
    public ResponseEntity<?> add(QuestionDto questionDto) {
        Question question = questionConverter.dtoToEntity(questionDto);
        service.add(question);
        return ResponseEntity.ok().build();
    }
}
