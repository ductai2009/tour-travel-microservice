package com.edo.auth_service.controller;

import com.edo.auth_service.dto.request.PermissionRequest;
import com.edo.auth_service.dto.request.ResponseData;

import com.edo.auth_service.dto.response.PermissionResponse;

import com.edo.auth_service.service.PermissionService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/permissions")
public class PermissionController {
	@Autowired
	private PermissionService permissionService;
	
	@PostMapping
	ResponseData<PermissionResponse> createPermission(@RequestBody @Valid PermissionRequest request) {

		ResponseData<PermissionResponse> response = new ResponseData<>();

		response.setMessage("Permission created successfully");
		response.setResult(permissionService.createPermission(request));

		return response;
	}

	@GetMapping
	ResponseData<List<PermissionResponse>> getAllPermission(){

		return ResponseData.<List<PermissionResponse>>builder()
				.result(permissionService.getAllPermission())
				.build();
	}


	@GetMapping("/{permissionName}")
	ResponseData<PermissionResponse> getPermission(@PathVariable("permissionName") String permissionName){

		return ResponseData.<PermissionResponse>builder()
				.result(permissionService.getPermission(permissionName))
				.build();
	}

	@PutMapping("/{permissionName}")
	ResponseData<PermissionResponse> updatePermission(@PathVariable String permissionName, @RequestBody PermissionRequest request){
		return ResponseData.<PermissionResponse>builder()
				.result(permissionService.updatePermission(permissionName, request))
				.build();
	}

	@DeleteMapping("/{permissionName}")
	ResponseData<String> deletePermission(@PathVariable String permissionName){
		permissionService.deletePermission(permissionName);
		return ResponseData.<String>builder()
				.result("Permission has been deleted")
				.build();
	}
	
}
