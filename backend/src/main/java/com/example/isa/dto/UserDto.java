package com.example.isa.dto;

import com.example.isa.model.AccountStatus;
import com.example.isa.model.Gender;
import com.example.isa.model.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private UUID id;
    private String personalId;
    private String email;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String gender;
    private boolean isVerified;

    public UserDto(User user) {
        this.id = user.getId();
        this.personalId = user.getPersonalId();
        this.email = user.getEmail();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.phoneNumber = user.getPhoneNumber();
        this.gender = user.getGender().toString();
        this.isVerified = user.isVerified();
    }
}
