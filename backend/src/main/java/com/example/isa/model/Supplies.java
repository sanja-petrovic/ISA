package com.example.isa.model;

import lombok.*;

import javax.persistence.*;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "supplies")

public class Supplies {
    @Id
    private UUID id;
    @Column
    private String name;
    @Column
    private int amount;
    @Builder
    public Supplies(int amount, String name, UUID id){
        this.amount = amount;
        this.name = name;
        this.id = id;
    }
}
