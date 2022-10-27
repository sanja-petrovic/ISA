package com.example.isa.model;

import lombok.*;

import javax.persistence.*;
import java.util.UUID;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "answers")
public class Answer {
    @Id
    private UUID id = UUID.randomUUID();

    @Column
    private boolean value;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "question_id")
    private Question question;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User user;

    @Builder
    public Answer(boolean value, Question question, User user) {
        this.value = value;
        this.question = question;
        this.user = user;
    }
}
