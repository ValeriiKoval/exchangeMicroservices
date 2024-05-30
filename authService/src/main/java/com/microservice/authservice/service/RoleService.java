package com.microservice.authservice.service;

import com.microservice.authservice.model.Role;
import com.microservice.authservice.model.RoleEnum;

public interface RoleService {

    Role findByName(RoleEnum name);
}
