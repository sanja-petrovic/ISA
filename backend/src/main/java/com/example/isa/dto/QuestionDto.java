package com.example.isa.dto;

import com.example.isa.model.Question;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class QuestionDto {
    private UUID id;
    private String text;
    private String type;
}

