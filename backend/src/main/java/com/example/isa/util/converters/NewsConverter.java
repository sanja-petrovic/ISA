package com.example.isa.util.converters;

import com.example.isa.dto.NewsDto;
import com.example.isa.model.News;
import com.example.isa.service.interfaces.BloodBankService;
import org.springframework.stereotype.Service;

@Service
public class NewsConverter implements Converter<News, NewsDto> {
    private final BloodBankService bloodBankService;

    public NewsConverter(BloodBankService bloodBankService) {
        this.bloodBankService = bloodBankService;
    }

    @Override
    public NewsDto entityToDto(News news) {
        return NewsDto.builder()
                .id(news.getId())
                .bloodBank(news.getBloodBank().getTitle())
                .title(news.getTitle())
                .body(news.getBody())
                .milliseconds(news.getMilliseconds())
                .build();
    }

    @Override
    public News dtoToEntity(NewsDto newsDto) {
        return News.builder()
                .title(newsDto.getTitle())
                .bloodBank(bloodBankService.findByTitle(newsDto.getBloodBank()))
                .body(newsDto.getBody())
                .build();
    }
}
