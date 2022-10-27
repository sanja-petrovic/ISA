package com.example.isa.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AnswerDto {
    private String questionText;
    private boolean answerValue;
    private String user;
}
