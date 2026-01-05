package com.jb98.springsecurity.demo_spring_security.controller.mapper;

import com.jb98.springsecurity.demo_spring_security.controller.request.UserRequest;
import com.jb98.springsecurity.demo_spring_security.entity.User;
import com.jb98.springsecurity.demo_spring_security.shared.GenericMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserRequestMapper extends GenericMapper<UserRequest, User> {
    @Mapping(target = "password", ignore = true)
    void updateEntity(UserRequest request, @MappingTarget User entity);
}
