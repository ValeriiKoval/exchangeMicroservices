package com.microservice.authservice.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "roles")
@NoArgsConstructor
@Getter
@Setter
public class Role extends IdBasedEntity implements Serializable {

    @Enumerated(EnumType.STRING)
    @Column(length = 20, unique = true)
    private RoleEnum name;

    public Role(RoleEnum name) {
        this.name = name;
    }

    public RoleEnum getName() {
        return name;
    }
}
