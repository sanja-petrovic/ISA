package com.example.isa.service.implementation;

import com.example.isa.dto.NewsDto;
import com.example.isa.kafka.NewsProducer;
import com.example.isa.model.News;
import com.example.isa.repository.NewsRepository;
import com.example.isa.service.interfaces.NewsService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NewsServiceImpl implements NewsService {

    private final NewsRepository repository;
    private final NewsProducer producer;

    public NewsServiceImpl(NewsRepository repository, NewsProducer producer) {
        this.repository = repository;
        this.producer = producer;
    }

    @Override
    public List<News> getAll() {
        return repository.findAll();
    }

    @Override
    public void create(News news) {
        repository.save(news);
    }

    @Override
    public void send(NewsDto dto) throws JsonProcessingException {
        producer.sendMessage(dto);
    }


}
