package com.jb98.springsecurity.demo_spring_security.mapper;

import com.jb98.springsecurity.demo_spring_security.dto.UserDto;
import com.jb98.springsecurity.demo_spring_security.entity.User;
import com.jb98.springsecurity.demo_spring_security.shared.GenericMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper extends GenericMapper<UserDto, User> {

}
