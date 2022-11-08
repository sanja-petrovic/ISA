package com.example.isa.dto;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AnswerDto {
    private UUID questionId;
    private boolean answerValue;
    private String user;
}
