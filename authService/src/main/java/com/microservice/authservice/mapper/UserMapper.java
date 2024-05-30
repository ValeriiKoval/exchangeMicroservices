package com.microservice.authservice.mapper;

import com.microservice.authservice.model.Role;
import com.microservice.authservice.model.RoleEnum;
import com.microservice.authservice.model.User;
import com.microservice.authservice.payload.response.UserResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;
import java.util.Set;

@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mapping(target = "id", source = "id")
    @Mapping(target = "username", source = "username")
    @Mapping(target = "email", source = "email")
    @Mapping(target = "roles", source = "roles", qualifiedByName = "convertRoles")
    UserResponse toUserResponse(User entity);

    @Named("convertRoles")
    static List<String> convertRoles(Set<Role> roles) {
        if (roles == null) {
            return List.of();
        }
        return roles.stream()
                .map(Role::getName)
                .map(RoleEnum::name)
                .toList();
    }
}
