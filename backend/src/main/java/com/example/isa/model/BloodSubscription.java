package com.example.isa.model;

import java.util.Date;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "blood_subscriptions",
uniqueConstraints = {@UniqueConstraint(columnNames = {"blood_bank_id", "type"})})

public class BloodSubscription {
	@Id
    private UUID id;
	@Column
    @Enumerated(EnumType.STRING)
    private BloodType type;
    @Column
    private double amount;
    @Column
    private Date deliveryDate;
    @ManyToOne
    @JoinColumn(name = "blood_bank_id")
    private BloodBank bloodBank;

}
