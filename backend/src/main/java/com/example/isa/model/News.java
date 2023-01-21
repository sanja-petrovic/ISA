package com.example.isa.model;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "news")
@NoArgsConstructor
public class News extends BaseEntity {
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "blood_bank_id")
    private BloodBank bloodBank;
    @Column
    private String title;
    @Column
    private String body;
    @Column
    private long milliseconds;

    @Builder
    public News(BloodBank bloodBank, String title, String body) {
        this.bloodBank = bloodBank;
        this.title = title;
        this.body = body;
        this.milliseconds = Instant.now().toEpochMilli();
    }
}
