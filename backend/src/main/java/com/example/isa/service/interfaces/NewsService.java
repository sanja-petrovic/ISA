package com.example.isa.service.interfaces;

import com.example.isa.dto.NewsDto;
import com.example.isa.model.News;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.List;

public interface NewsService {
    List<News> getAll();
    void create(News news) throws JsonProcessingException;
    void send(NewsDto dto) throws JsonProcessingException;
}
