package com.example.isa.service;

import com.example.isa.model.News;

import java.util.List;

public interface NewsService {
    public List<News> getAll();

    public void create(News news);
}
