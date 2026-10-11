package com.jb98.springsecurity.demo_spring_security.controller.mapper;

import com.jb98.springsecurity.demo_spring_security.controller.request.CreateUserRequest;
import com.jb98.springsecurity.demo_spring_security.controller.request.UpdateUserRequest;
import com.jb98.springsecurity.demo_spring_security.entity.User;
import com.jb98.springsecurity.demo_spring_security.shared.GenericMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserRequestMapper extends GenericMapper<CreateUserRequest, User> {
    @Mapping(target = "password", ignore = true)
    void updateEntity(UpdateUserRequest request, @MappingTarget User entity);
}
