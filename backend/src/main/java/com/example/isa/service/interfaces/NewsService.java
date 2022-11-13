package com.example.isa.service.interfaces;

import com.example.isa.dto.NewsDto;
import com.example.isa.model.News;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.List;

public interface NewsService {
    public List<News> getAll();
    public void create(News news) throws JsonProcessingException;
    public void send(NewsDto dto) throws JsonProcessingException;
}
