package com.example.isa.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@Table(name = "users")
@Entity
public class User {
    @Id
    private UUID id;
    @Column(unique = true)
    private String email;
    @Column
    private String password;
    @Column
    private String firstName;
    @Column
    private String lastName;
    @ManyToOne
    @JoinColumn(name = "address_id")
    private Address address;
    @Column(unique = true)
    private String phoneNumber;
    @Column(unique = true)
    private String personalId;
    @Column
    @Enumerated(value = EnumType.STRING)
    private Gender gender;
    @Column
    private String occupation;
    @Column
    private String institutionInfo;
    @Column
    @Enumerated(value = EnumType.STRING)
    private Role role;

    public User(String email, String password, String firstName, String lastName, Address address, String phoneNumber, String personalId, Gender gender, String occupation, String institutionInfo, Role role) {
        this.id = UUID.randomUUID();
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.personalId = personalId;
        this.gender = gender;
        this.occupation = occupation;
        this.institutionInfo = institutionInfo;
        this.role = role;
    }
}
