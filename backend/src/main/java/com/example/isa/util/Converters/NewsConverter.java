package com.example.isa.util.Converters;

import com.example.isa.dto.NewsDto;
import com.example.isa.model.News;
import org.springframework.stereotype.Service;

@Service
public class NewsConverter implements Converter<News, NewsDto> {
    @Override
    public NewsDto entityToDto(News news) {
        return null;
    }

    @Override
    public News dtoToEntity(NewsDto newsDto) {
        return null;
    }
}
