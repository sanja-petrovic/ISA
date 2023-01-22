package com.example.isa.model;

import lombok.*;

import javax.persistence.*;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name="questions")
public class Question extends BaseEntity {
    @Column
    private String text;
    @Enumerated(EnumType.STRING)
    private QuestionType type;
}
