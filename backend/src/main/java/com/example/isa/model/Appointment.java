package com.example.isa.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

import com.example.isa.util.DateConvertor;

import ch.qos.logback.classic.pattern.DateConverter;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "appointments")
public class Appointment {
    @Id
    @Column
    private UUID uuid;
    @Column
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateTime;
    @Column
    private Long duration;
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn
    private BloodBank bloodBank;
    @ManyToOne(optional = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_id")
    private Patient patient;
    
    public boolean hasDateTimeOverlap(LocalDateTime date, Long duration) {
    	LocalDateTime storedDateTime = DateConvertor.convert(dateTime);
    	
    	if( (storedDateTime.compareTo(date.plusMinutes(duration))<=0) && (storedDateTime.plusMinutes(this.duration).compareTo(date)<=0) ){
    		return true;
    	}
    	return false;
    }
}
