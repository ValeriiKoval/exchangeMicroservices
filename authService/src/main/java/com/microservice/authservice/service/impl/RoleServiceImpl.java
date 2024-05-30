package com.microservice.authservice.service.impl;

import com.microservice.authservice.model.Role;
import com.microservice.authservice.model.RoleEnum;
import com.microservice.authservice.repository.RoleRepository;
import com.microservice.authservice.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    @Override
    public Role findByName(RoleEnum name) {
        return roleRepository.findByName(name).orElse(new Role(name));
    }

}
