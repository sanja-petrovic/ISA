package com.example.isa.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor

@Table(name = "addresses", uniqueConstraints={@UniqueConstraint(columnNames ={"street","number", "city", "country"})})
@Entity
public class Address {
    @Id
    private UUID id;
    @Column
    private String street;
    @Column
    private String number;
    @Column
    private String city;
    @Column
    private String country;

    public Address(String street, String number, String city, String country) {
        this.id = UUID.randomUUID();
        this.street = street;
        this.number = number;
        this.city = city;
        this.country = country;
    }
}
