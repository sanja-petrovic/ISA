package com.example.isa.model;


import lombok.*;

import java.time.Instant;
import java.util.UUID;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name="refresh_tokens")
@Entity(name = "refreshtoken")
@Builder
public class RefreshToken {
    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @Id
    @Column(nullable = false, unique = true)
    private String token;

    @Column(nullable = false)
    private Long expiryDate;
}