package com.example.isa.model;

import com.example.isa.model.locator.Location;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@Entity
@AllArgsConstructor
@Table(name = "blood_banks")
public class BloodBank extends BaseEntity {
    @Column(unique = true)
    private String title;
    @Embedded
    private Address address;
    @Embedded
    private Interval workingHours;
    @Column
    private String description;
    @Column
    private double averageGrade;
    @JsonBackReference
    @OneToMany(mappedBy = "bloodBank", cascade = CascadeType.ALL)
    private Set<MedicalStaff> medicalStaff;
    @OneToMany(mappedBy = "bloodBank", cascade = CascadeType.ALL)
    private Set<BloodSubscription> subscriptions;
    @JsonBackReference
    @OneToMany(mappedBy = "bloodBank", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<BloodSupply> bloodSupplies;

    @OneToMany(mappedBy = "bloodBank", orphanRemoval = true)
    private Set<News> news = new LinkedHashSet<>();

    @Embedded
    private Location location;

    public BloodBank(String title, Address address, String description) {
        this.title = title;
        this.address = address;
        this.description = description;
    }

    @Builder
    public BloodBank(String title, Address address, Interval workingHours, String description, double averageGrade) {
        this.title = title;
        this.address = address;
        this.workingHours = workingHours;
        this.description = description;
        this.averageGrade = averageGrade;
    }

    public BloodBank(String title, Address address, String description, Interval workingHours, double averageGrade) {
        this.title = title;
        this.address = address;
        this.description = description;
        this.workingHours = workingHours;
        this.averageGrade = averageGrade;
    }
    
    public boolean checkBloodSupply(BloodType type, double amount) {
    	for(BloodSupply supply : this.bloodSupplies) {
    		if(supply.getType().equals(type) && supply.getAmount()>=amount) {
    			return true;
    		}
    	}
    	return false;
    }

}
