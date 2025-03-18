package com.edo.auth_service.mapper;

import com.edo.auth_service.dto.request.RoleRequest;
import com.edo.auth_service.dto.request.UserCreationRequest;
import com.edo.auth_service.dto.request.UserUpdateRequest;
import com.edo.auth_service.dto.response.RoleResponse;
import com.edo.auth_service.dto.response.UserResponse;
import com.edo.auth_service.entity.Role;
import com.edo.auth_service.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface RoleMapper {

    @Mapping(target = "permissions", ignore = true)
    Role toRole(RoleRequest request);

    RoleResponse toRoleResponse(Role role);

    @Mapping(target = "permissions", ignore = true)
    Role toUpdateRole(@MappingTarget Role role, RoleRequest roleRequest);

  }


