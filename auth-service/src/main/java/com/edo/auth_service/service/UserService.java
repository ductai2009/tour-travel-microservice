package com.edo.auth_service.service;

import com.edo.auth_service.dto.request.RoleRequest;
import com.edo.auth_service.dto.request.UserUpdateRequest;
import com.edo.auth_service.dto.response.PermissionResponse;
import com.edo.auth_service.dto.response.UserResponse;
import com.edo.auth_service.entity.Role;


import com.edo.auth_service.exception.AppException;
import com.edo.auth_service.exception.ErrorCode;
import com.edo.auth_service.mapper.UserMapper;
import com.edo.auth_service.repository.PermissionRepository;
import com.edo.auth_service.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.edo.auth_service.dto.request.UserCreationRequest;
import com.edo.auth_service.entity.User;
import com.edo.auth_service.repository.UserRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Slf4j
@Service
public class UserService {
	@Autowired
	private UserRepository userRepository ;
	@Autowired
	private UserMapper userMapper;

	@Autowired
	RoleRepository roleRepository;

	@Autowired
	PermissionRepository permissionRepository;

	@Autowired
	PermissionService permissionService;
	@Autowired
	PasswordEncoder passwordEncoder;


	public List<PermissionResponse> getAuthority(){

		List<PermissionResponse> permissionResponses = permissionService.getAllPermission();

		return permissionResponses;
	}


	public UserResponse createUser(UserCreationRequest request) {

		if(userRepository.existsByUsername(request.getUsername()))
			throw new AppException(ErrorCode.USER_EXISTED);

		User user = userMapper.toUser(request);

		user.setPassword(passwordEncoder.encode(request.getPassword()));

		HashSet<Role> roles = new HashSet<>();

		roleRepository.findAllByNameIn(request.getRole()).forEach(roles::add);


		user.setRole(roles);

		return userMapper.toUserResponse(userRepository.save(user));
	}

	@PreAuthorize("hasRole('ADMIN')")
	public UserResponse updateUser(String userId, UserUpdateRequest request) {

		User user = userRepository.findById(userId)
				.orElseThrow(() -> new AppException(ErrorCode.NOT_FOUND));

		User userUpdate = userMapper.updateUser(user, request);

		HashSet<Role> roles = new HashSet<>();


		roleRepository.findAllByNameIn(request.getRole()).forEach(roles::add);

		userUpdate.setRole(roles);

		return userMapper.toUserResponse(userRepository.save(userUpdate));
	}

	@PreAuthorize("hasRole('ADMIN')")
	public UserResponse addRoleUser(String userId, RoleRequest request) {

		User user = userRepository.findById(userId)
				.orElseThrow(() -> new AppException(ErrorCode.NOT_FOUND));

		Role role = roleRepository.findByName(request.getName())
				.orElseThrow(() -> new AppException(ErrorCode.ROLE_NOT_EXISTED));


		HashSet<Role> roles = new HashSet<>(user.getRole());
		roles.add(role);

		user.setRole(roles);

		return userMapper.toUserResponse(userRepository.save(user));
	}

	@PreAuthorize("hasAuthority('DELETE_USER')")
	public void deleteUser(String userId){
		userRepository.deleteById(userId);
	}

	@PreAuthorize("hasRole('ADMIN')")
	public List<UserResponse> getUsers(){
		return userRepository.findAll().stream().map(user -> userMapper.toUserResponse(user)).toList();
	}

	@PostAuthorize("returnObject.username == authentication.name")
	public UserResponse getUser(String id){
		return userMapper.toUserResponse(userRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("User not found")));
	}

	public UserResponse getMyInfo(){
		var authentication = SecurityContextHolder.getContext().getAuthentication();
		String username = authentication.getName();
//		String token = authentication.getCredentials().toString();
		var user = userRepository.findByUsername(username).orElseThrow(() ->new AppException(ErrorCode.USER_NOT_EXISTED));

		return userMapper.toUserResponse(user);
	}
	
}
