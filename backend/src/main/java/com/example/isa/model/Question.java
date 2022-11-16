package com.example.isa.model;

import lombok.*;

import javax.persistence.*;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="questions")
public class Question {
    @Id
    private UUID id = UUID.randomUUID();
    @Column
    private String text;

    @Enumerated(EnumType.STRING)
    private QuestionType type;

    @Builder
    public Question(String text, QuestionType type) {
        this.text = text;
        this.type = type;
    }
}
