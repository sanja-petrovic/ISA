package com.example.isa.service.implementation;

import com.example.isa.kafka.NewsProducer;
import com.example.isa.model.News;
import com.example.isa.repository.NewsRepository;
import com.example.isa.service.NewsService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NewsServiceImpl implements NewsService {

    private final NewsRepository repository;

    public NewsServiceImpl(NewsRepository repository, NewsProducer newsProducer) {
        this.repository = repository;
    }

    @Override
    public List<News> getAll() {
        return repository.findAll();
    }

    @Override
    public void create(News news) {
        repository.save(news);
    }


}
