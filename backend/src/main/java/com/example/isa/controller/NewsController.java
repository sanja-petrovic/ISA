package com.example.isa.controller;

import com.example.isa.dto.NewsDto;
import com.example.isa.model.News;
import com.example.isa.service.interfaces.NewsService;
import com.example.isa.util.converters.NewsConverter;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@Api(value = "/news")
@RequestMapping(value = "/news")
public class NewsController {

    private final NewsService newsService;
    private final NewsConverter newsConverter;

    public NewsController(NewsService newsService, NewsConverter newsConverter) {
        this.newsService = newsService;
        this.newsConverter = newsConverter;
    }

    @GetMapping
    @ApiOperation(value = "Get all news.", httpMethod = "GET")
    ResponseEntity<List<NewsDto>> getAll() {
        return ResponseEntity.ok(newsService.getAll().stream().map(newsConverter::entityToDto).collect(Collectors.toList()));
    }

    @PostMapping
    @ApiOperation(value = "Create news.", httpMethod = "POST")
    ResponseEntity<?> createNews(@RequestBody NewsDto dto) throws JsonProcessingException {
        News news = newsConverter.dtoToEntity(dto);
        newsService.create(news);
        return ResponseEntity.ok().build();
    }

}
