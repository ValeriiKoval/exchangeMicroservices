package com.microservice.authservice.repository;

import com.microservice.authservice.model.Role;
import com.microservice.authservice.model.RoleEnum;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findByName(RoleEnum name);
}
