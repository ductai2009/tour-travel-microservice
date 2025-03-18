package com.edo.auth_service.mapper;

import com.edo.auth_service.dto.request.PermissionRequest;
import com.edo.auth_service.dto.response.PermissionResponse;
import com.edo.auth_service.entity.Permission;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface PermissionMapper {


    Permission toPermission(PermissionRequest request);

    PermissionResponse toPermissionResponse(Permission permission);

    Permission toUpdatePermission(@MappingTarget Permission permission, PermissionRequest permissionRequest);

  }


