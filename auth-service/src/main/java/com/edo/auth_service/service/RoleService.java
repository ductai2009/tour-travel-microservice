package com.edo.auth_service.service;


import com.edo.auth_service.dto.request.PermissionRequest;
import com.edo.auth_service.dto.request.RoleRequest;
import com.edo.auth_service.dto.response.RoleResponse;
import com.edo.auth_service.entity.Permission;
import com.edo.auth_service.entity.Role;
import com.edo.auth_service.exception.AppException;
import com.edo.auth_service.exception.ErrorCode;
import com.edo.auth_service.mapper.RoleMapper;
import com.edo.auth_service.repository.PermissionRepository;
import com.edo.auth_service.repository.RoleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class RoleService {

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PermissionRepository permissionRepository;

    @Autowired
    RoleMapper roleMapper;

    public RoleResponse createRole(RoleRequest request){
        if(roleRepository.existsByName(request.getName()))
            throw new AppException(ErrorCode.ROLE_EXISTED);
        Role role = roleMapper.toRole(request);

        List<String> permissionIds = request.getPermissions()
                .stream()
                .filter(Objects::nonNull)
                .filter(id -> !id.trim().isEmpty())
                .toList();


        if (permissionIds.isEmpty()) {
            throw new AppException(ErrorCode.PERMISSION_IN_ROLE_EXISTED);
        }

        List<Permission> permissions = permissionRepository.findAllById(permissionIds);

        role.setPermissions(new HashSet<Permission>(permissions));

        try {

            return roleMapper.toRoleResponse(roleRepository.save(role));

        } catch (JpaSystemException e) {

            throw new AppException(ErrorCode.ROLE_EXISTED);

        }

    }

    public RoleResponse updateRole(String roleName,RoleRequest request){

        Role role =  roleRepository.findById(roleName).orElseThrow(() ->
                new AppException(ErrorCode.NOT_FOUND));


        Role roleUpdate  = roleMapper.toUpdateRole(role, request);

        return roleMapper.toRoleResponse(roleRepository.save(roleUpdate));
    }
    public List<RoleResponse> getAllRole(){
        List<Role> role = roleRepository.findAll();

        return role.stream().map(roleMap -> roleMapper.toRoleResponse(roleMap)).toList();
    }
    public RoleResponse getRole(String roleName){
        Role role = roleRepository.findByName(roleName).orElseThrow(() ->
                new AppException(ErrorCode.ROLE_NOT_EXISTED));

        return roleMapper.toRoleResponse(role);
    }
    public RoleResponse addPermissionInRole(String roleName, PermissionRequest request){
        Role role = roleRepository.findByName(roleName).orElseThrow(() ->
                new AppException(ErrorCode.ROLE_NOT_EXISTED));


        HashSet<Permission> permissions = new HashSet<>(role.getPermissions());

        Permission permission = permissionRepository.findByName(request.getName()).orElseThrow(() ->
                new AppException(ErrorCode.PERMISSION_NOT_EXISTED));


        permissions.add(permission);
        role.setPermissions(permissions);

        return roleMapper.toRoleResponse(roleRepository.save(role));
    }

    public void deleteRole(String roleName){
        roleRepository.deleteById(roleName);
    }


}
