package com.example.isa.controller;

import com.example.isa.dto.QuestionDto;
import com.example.isa.dto.QuestionListDto;
import com.example.isa.model.Question;
import com.example.isa.model.QuestionType;
import com.example.isa.service.interfaces.QuestionService;
import io.swagger.models.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/questions")
public class QuestionController {

    private final QuestionService service;

    public QuestionController(QuestionService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<QuestionDto>> getAll() {
        List<Question> questions = service.getAll();
        List<QuestionDto> questionDtos = questions.stream().map(question -> new QuestionDto(question)).collect(Collectors.toList());
        QuestionListDto dtoList = new QuestionListDto(questionDtos);
        return ResponseEntity.ok(questionDtos);
    }

    @PostMapping
    public ResponseEntity<?> add(QuestionDto questionDto) {
        QuestionType type = Objects.equals(questionDto.getType(), "FOR_ALL") ? QuestionType.FOR_ALL : QuestionType.FOR_WOMEN;
        Question question = Question.builder().text(questionDto.getText()).type(type).build();
        service.add(question);
        return ResponseEntity.ok().build();
    }
}
