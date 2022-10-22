package com.example.isa.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class Period {
    @Column
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime start;
    @Column
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime end;
}
