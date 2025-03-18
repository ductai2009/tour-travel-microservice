package com.edo.auth_service.controller;

import com.edo.auth_service.dto.request.PermissionRequest;
import com.edo.auth_service.dto.request.ResponseData;
import com.edo.auth_service.dto.request.RoleRequest;
import com.edo.auth_service.dto.request.UserUpdateRequest;
import com.edo.auth_service.dto.response.RoleResponse;
import com.edo.auth_service.dto.response.UserResponse;
import com.edo.auth_service.service.RoleService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/roles")
public class RoleController {
	@Autowired
	private RoleService roleService;
	
	@PostMapping
	ResponseData<RoleResponse> createRole(@RequestBody @Valid RoleRequest request) {
		ResponseData<RoleResponse> response = new ResponseData<>();

		response.setMessage("Role created successfully");
		response.setResult(roleService.createRole(request));

		return response;
	}

	@GetMapping
	ResponseData<List<RoleResponse>> getRoles(){

		return ResponseData.<List<RoleResponse>>builder()
				.result(roleService.getAllRole())
				.build();
	}


	@GetMapping("/{roleName}")
	ResponseData<RoleResponse> getRole(@PathVariable("roleName") String roleName){

		return ResponseData.<RoleResponse>builder()
				.result(roleService.getRole(roleName))
				.build();
	}

	@PutMapping("/{roleName}")
	ResponseData<RoleResponse> updateUser(@PathVariable String roleName, @RequestBody RoleRequest request){
		return ResponseData.<RoleResponse>builder()
				.result(roleService.updateRole(roleName, request))
				.build();
	}
	@PutMapping("/add-permission/{roleName}")
	ResponseData<RoleResponse> addPermissionInRole(@PathVariable String roleName, @RequestBody PermissionRequest request){
		return ResponseData.<RoleResponse>builder()
				.result(roleService.addPermissionInRole(roleName, request))
				.build();
	}

	@DeleteMapping("/{roleName}")
	ResponseData<String> deleteUser(@PathVariable String roleName){
		roleService.deleteRole(roleName);
		return ResponseData.<String>builder()
				.result("Role has been deleted")
				.build();
	}
	
}
