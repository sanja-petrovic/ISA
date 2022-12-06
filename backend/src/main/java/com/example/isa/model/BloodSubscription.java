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
import springfox.documentation.swagger.web.SwaggerApiListingReader;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "blood_subscriptions",
uniqueConstraints = {@UniqueConstraint(columnNames = {"blood_bank_id", "type"})})

public class BloodSubscription {
	@Id
    private UUID id = UUID.randomUUID();
	@Column
    @Enumerated(EnumType.STRING)
    private BloodType type;
    @Column
    private double amount;
    @Column
    private int deliveryDate;
    @Column
    private boolean active;
    @Column
    private String originId; 
    @ManyToOne
    @JoinColumn(name = "blood_bank_id")
    private BloodBank bloodBank;
	public BloodSubscription(BloodType type, double amount, int deliveryDate, boolean active,String originid, BloodBank bloodBank) {
		super();
		this.id = UUID.randomUUID();
		this.type = type;
		this.amount = amount;
		this.deliveryDate = deliveryDate;
		this.active = active;
		this.originId = originid;
		this.bloodBank = bloodBank;
	}

}
