package com.jb98.springsecurity.demo_spring_security.controller.mapper;

import com.jb98.springsecurity.demo_spring_security.controller.request.RoleRequest;
import com.jb98.springsecurity.demo_spring_security.entity.Role;
import com.jb98.springsecurity.demo_spring_security.shared.GenericMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RoleRequestMapper extends GenericMapper<RoleRequest, Role> {
}
