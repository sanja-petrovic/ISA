package com.example.isa.controller;

import com.example.isa.dto.QuestionDto;
import com.example.isa.dto.QuestionListDto;
import com.example.isa.model.CacheData;
import com.example.isa.model.Question;
import com.example.isa.service.interfaces.CacheDataService;
import com.example.isa.service.interfaces.QuestionService;
import com.example.isa.util.converters.QuestionConverter;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/questions")
@Api(value = "/questions", tags = "Questionnaire questions")
public class QuestionController {
    private final QuestionService service;
    private final QuestionConverter questionConverter;
    private final CacheDataService cacheDataService;
    private final ObjectMapper objectMapper;

    public QuestionController(QuestionService service, QuestionConverter questionConverter, CacheDataService cacheDataService, ObjectMapper objectMapper) {
        this.service = service;
        this.questionConverter = questionConverter;
        this.cacheDataService = cacheDataService;
        this.objectMapper = objectMapper;
    }

    @GetMapping
    @ApiOperation(value = "Get all questions.", httpMethod = "GET")
    public ResponseEntity<QuestionListDto> getAll() throws JsonProcessingException {
        CacheData cachedQuestions = cacheDataService.get("allQuestions");
        if(cachedQuestions != null) {
            return ResponseEntity.ok(objectMapper.readValue(cachedQuestions.getValue(), QuestionListDto.class));
        } else {
            QuestionListDto questionListDto = new QuestionListDto(service.getAll().stream().map(questionConverter::entityToDto).collect(Collectors.toList()));
            cacheDataService.add("allQuestions", objectMapper.writeValueAsString(questionListDto));
            return ResponseEntity.ok(questionListDto);
        }
    }

    @PostMapping
    @ApiOperation(value = "Add a new question.", httpMethod = "POST")
    public ResponseEntity<?> add(QuestionDto questionDto) {
        Question question = questionConverter.dtoToEntity(questionDto);
        service.add(question);
        return ResponseEntity.ok().build();
    }
}
