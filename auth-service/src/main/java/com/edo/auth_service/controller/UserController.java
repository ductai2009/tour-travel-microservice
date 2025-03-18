package com.edo.auth_service.controller;

import com.edo.auth_service.dto.request.ResponseData;
import com.edo.auth_service.dto.request.RoleRequest;
import com.edo.auth_service.dto.request.UserUpdateRequest;
import com.edo.auth_service.dto.response.UserResponse;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import com.edo.auth_service.dto.request.UserCreationRequest;
import com.edo.auth_service.service.UserService;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {
	@Autowired
	private UserService userService;
	
	@PostMapping
	ResponseData<UserResponse> createUser(@RequestBody @Valid UserCreationRequest request) {
		ResponseData<UserResponse> response = new ResponseData<>();

		response.setCode(1000);
		response.setMessage("User created successfully");
		response.setResult(userService.createUser(request));

		return response;
	}

	@GetMapping
	ResponseData<List<UserResponse>> getUsers(){
		var authentication = SecurityContextHolder.getContext().getAuthentication();
		log.info(">>>> Name : {}", authentication.getName());

		authentication.getAuthorities().forEach(role -> log.info(">>>> Role : {}", role.getAuthority()));
		return ResponseData.<List<UserResponse>>builder()
				.result(userService.getUsers())
				.build();
	}
	@GetMapping("/info")
	ResponseData<UserResponse> getMyInfo(){

		return ResponseData.<UserResponse>builder()
				.result(userService.getMyInfo())
				.build();
	}

	@GetMapping("/{userId}")
	ResponseData<UserResponse> getUser(@PathVariable("userId") String userId){

		return ResponseData.<UserResponse>builder()
				.result(userService.getUser(userId))
				.build();
	}

	@PutMapping("/{userId}")
	ResponseData<UserResponse> updateUser(@PathVariable String userId, @RequestBody UserUpdateRequest request){
		return ResponseData.<UserResponse>builder()
				.result(userService.updateUser(userId, request))
				.build();
	}

	@PutMapping("/add-role/{userId}")
	ResponseData<UserResponse> updateUser(@PathVariable String userId, @RequestBody RoleRequest request){

		return ResponseData.<UserResponse>builder()
				.result(userService.addRoleUser(userId, request))
				.build();
	}

	@DeleteMapping("/{userId}")
	ResponseData<String> deleteUser(@PathVariable String userId){
		userService.deleteUser(userId);
		return ResponseData.<String>builder()
				.result("User has been deleted")
				.build();
	}
	
}
