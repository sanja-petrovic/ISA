package com.example.isa.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.UUID;

@MappedSuperclass
@Setter
@Getter
public abstract class BaseEntity {
    @Id
    private UUID id = UUID.randomUUID();

    @Version
    private Integer version = 1;
}
