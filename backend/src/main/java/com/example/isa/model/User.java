package com.example.isa.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "users")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class User extends BaseEntity implements UserDetails{
    @Column(unique = true)
    private String personalId;
    @Column(unique = true)
    private String email;
    @Column
    private String password;
    @Column
    private String firstName;
    @Column
    private String lastName;
    @Column(unique = true)
    private String phoneNumber;
    @Column
    @Enumerated(value = EnumType.STRING)
    private Gender gender;
    @Column
    private boolean isVerified;
    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;

    public User(String personalId, String email, String password, String firstName, String lastName, String phoneNumber, Gender gender, boolean verified, Role role) {
        this.personalId = personalId;
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.gender = gender;
        this.isVerified = verified;
        this.role = role;
    }

    public User(String personalId, String email, String password, String firstName, String lastName, String phoneNumber, Gender gender) {
        this.personalId = personalId;
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.gender = gender;
    }

    @JsonIgnore
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        ArrayList<Role> roles = new ArrayList<>();
        roles.add(this.role);
        return roles;
    }

    @Override
    public String getUsername() {
        return this.getEmail();
    }
    @JsonIgnore
    @Override
    public boolean isAccountNonExpired() {
        return isVerified;
    }
    @JsonIgnore
    @Override
    public boolean isAccountNonLocked() {
        return isVerified;
    }
    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired() {
        return isVerified;
    }
    @JsonIgnore
    @Override
    public boolean isEnabled() {
        return isVerified;
    }
}
