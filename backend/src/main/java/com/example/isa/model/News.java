package com.example.isa.model;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "news")
@NoArgsConstructor
public class News {
    @Id
    private UUID id = UUID.randomUUID();
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "blood_bank_id")
    private BloodBank bloodBank;
    @Column
    private String title;
    @Column
    private String body;
    @Column
    @Temporal(TemporalType.DATE)
    private Date timestamp;

    @Builder
    public News(BloodBank bloodBank, String title, String body, Date timestamp) {
        this.bloodBank = bloodBank;
        this.title = title;
        this.body = body;
        this.timestamp = timestamp;
    }
}
