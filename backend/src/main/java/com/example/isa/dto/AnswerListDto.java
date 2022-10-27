package com.example.isa.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class AnswerListDto {
    List<AnswerDto> answers;
}
