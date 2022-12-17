package com.example.isa.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

import com.example.isa.util.converters.DateConverter;

import java.time.LocalDateTime;
import java.util.Date;
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
    @JoinColumn(name = "blood_donor_id")
    private BloodDonor bloodDonor;
    
    public boolean hasDateTimeOverlap(LocalDateTime date, Long duration) {
    	LocalDateTime storedDateTime = DateConverter.convert(dateTime);
    	
    	if( (!storedDateTime.isAfter(date.plusMinutes(duration))) && (!storedDateTime.plusMinutes(this.duration).isAfter(date)) ){
    		return true;
    	}
    	return false;
    }
}
