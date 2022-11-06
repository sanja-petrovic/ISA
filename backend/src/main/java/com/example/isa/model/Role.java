package com.example.isa.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name="roles")
@NoArgsConstructor
public class Role implements GrantedAuthority {
    @Id
    UUID id = UUID.randomUUID();

    @Column(name="name")
    String name;

    @JsonIgnore
    @Override
    public String getAuthority() {
        return name;
    }
    public Role(String name) {
        this.name = name;
    }
}
