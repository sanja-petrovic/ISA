package com.example.isa.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "blood_supplies", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"type", "blood_bank_id"})
})
public class BloodSupply {
    @Id
    private UUID id;
    @Column
    @Enumerated(EnumType.STRING)
    private BloodType type;
    @Column
    private double amount;
    @JsonManagedReference
    @ManyToOne
    @JoinColumn(name = "blood_bank_id")
    private BloodBank bloodBank;

}
