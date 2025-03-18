package com.edo.auth_service.mapper;

import com.edo.auth_service.dto.request.UserCreationRequest;
import com.edo.auth_service.dto.request.UserUpdateRequest;
import com.edo.auth_service.dto.response.UserResponse;
import com.edo.auth_service.entity.User;
import com.edo.auth_service.repository.UserRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "role", ignore = true)
    User toUser(UserCreationRequest request);

    UserResponse toUserResponse(User user);

    @Mapping(target = "password", ignore = true)
    @Mapping(target = "role", ignore = true)
    User updateUser(@MappingTarget User user, UserUpdateRequest request);
  }


