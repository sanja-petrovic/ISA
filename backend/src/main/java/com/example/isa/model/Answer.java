package com.example.isa.model;

import lombok.*;

import javax.persistence.*;
import java.util.UUID;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "answers")
public class Answer extends BaseEntity {
    @Column
    private boolean value;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "question_id")
    private Question question;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "blood_donor_id")
    private BloodDonor bloodDonor;

    @Builder
    public Answer(boolean value, Question question, BloodDonor bloodDonor) {
        this.value = value;
        this.question = question;
        this.bloodDonor = bloodDonor;
    }
}
