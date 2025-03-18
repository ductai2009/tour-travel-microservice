package com.edo.auth_service.service;


import com.edo.auth_service.dto.request.PermissionRequest;
import com.edo.auth_service.dto.request.RoleRequest;
import com.edo.auth_service.dto.response.PermissionResponse;
import com.edo.auth_service.dto.response.RoleResponse;
import com.edo.auth_service.entity.Permission;
import com.edo.auth_service.entity.Role;
import com.edo.auth_service.exception.AppException;
import com.edo.auth_service.exception.ErrorCode;
import com.edo.auth_service.mapper.PermissionMapper;
import com.edo.auth_service.repository.PermissionRepository;
import com.edo.auth_service.repository.RoleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class PermissionService {

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PermissionRepository permissionRepository;

    @Autowired
    PermissionMapper permissionMapper;

    public PermissionResponse createPermission(PermissionRequest request){

        if(permissionRepository.existsByName(request.getName()))
            throw new AppException(ErrorCode.PERMISSION_EXISTED);

        Permission permission = permissionMapper.toPermission(request);

        try {
            return permissionMapper.toPermissionResponse(permissionRepository.save(permission));
        } catch (DataIntegrityViolationException e) {
            throw new AppException(ErrorCode.PERMISSION_EXISTED);
        }

    }
    public PermissionResponse getPermission(String permissionName){
        Permission permission = permissionRepository.findByName(permissionName).orElseThrow(() ->
                new AppException(ErrorCode.PERMISSION_NOT_EXISTED));

        return permissionMapper.toPermissionResponse(permission);
    }

    public PermissionResponse updatePermission(String permissionName,PermissionRequest request){

        Permission permission = permissionRepository.findById(permissionName).orElseThrow(() ->
                new AppException(ErrorCode.NOT_FOUND));

        Permission permissionUpdate  = permissionMapper.toUpdatePermission(permission, request);

        return permissionMapper.toPermissionResponse(permissionRepository.save(permissionUpdate));
    }
    public List<PermissionResponse> getAllPermission(){

        List<Permission> permissions = permissionRepository.findAll();

        return permissions.stream().map(permission -> permissionMapper.toPermissionResponse(permission)).toList();
    }

    public void deletePermission(String permissionName){
        permissionRepository.deleteById(permissionName);
    }


}
