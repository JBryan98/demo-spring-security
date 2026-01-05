package com.jb98.springsecurity.demo_spring_security.mapper;

import com.jb98.springsecurity.demo_spring_security.dto.RoleDto;
import com.jb98.springsecurity.demo_spring_security.entity.Role;
import com.jb98.springsecurity.demo_spring_security.shared.GenericMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RoleMapper extends GenericMapper<RoleDto, Role> {
}
