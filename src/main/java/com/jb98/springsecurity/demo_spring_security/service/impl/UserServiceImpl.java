package com.jb98.springsecurity.demo_spring_security.service.impl;

import com.jb98.springsecurity.demo_spring_security.controller.mapper.UserRequestMapper;
import com.jb98.springsecurity.demo_spring_security.controller.request.ChangePasswordRequest;
import com.jb98.springsecurity.demo_spring_security.controller.request.UserRequest;
import com.jb98.springsecurity.demo_spring_security.dto.UserDto;
import com.jb98.springsecurity.demo_spring_security.entity.User;
import com.jb98.springsecurity.demo_spring_security.exception.BusinessException;
import com.jb98.springsecurity.demo_spring_security.mapper.UserMapper;
import com.jb98.springsecurity.demo_spring_security.repository.UserRepository;
import com.jb98.springsecurity.demo_spring_security.service.IRoleService;
import com.jb98.springsecurity.demo_spring_security.service.IUserService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements IUserService {
    private final UserRepository repository;
    private final UserMapper mapper;
    private final UserRequestMapper rqMapper;
    private final IRoleService roleService;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional(readOnly = true)
    public Page<UserDto> findAll(Pageable pageable) {
        log.info("findAll pageable {}", pageable);
        var pageOfIds = repository.findAllIdsOnly(pageable);
        var ids = Set.copyOf(pageOfIds.getContent());
        var users = repository.findByIdIn(ids);
        return new PageImpl<>(mapper.toDto(users), pageable, pageOfIds.getTotalElements());
    }

    @Override
    @Transactional(readOnly = true)
    public User find(Long id) {
        log.info("findByIdOrThrowException {}", id);
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + id));
    }

    @Override
    @Transactional(readOnly = true)
    public UserDto findDto(Long id) {
        log.info("findById {}", id);
        var user = find(id);
        return mapper.toDto(user);
    }

    @Override
    public UserDto create(UserRequest request) {
        log.info("create request {}", request);
        validateUqUsername(request.username());
        validateUqEmail(request.email());
        var user = rqMapper.toEntity(request);
        populateUserRoles(user, request.roleIds());
        user.setPassword(passwordEncoder.encode(request.password()));
        repository.save(user);
        return mapper.toDto(user);
    }

    @Override
    public UserDto update(Long id, UserRequest request) {
        log.info("update id {} request {}", id, request);
        var user = find(id);
        if (!user.getUsername().equals(request.username())) {
            validateUqUsername(request.username());
        }
        if (!user.getEmail().equals(request.email())) {
            validateUqEmail(request.email());
        }
        rqMapper.updateEntity(request, user);
        populateUserRoles(user, request.roleIds());
        repository.save(user);
        return mapper.toDto(user);
    }

    @Override
    public UserDto findCurrentUser() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated() || authentication instanceof AnonymousAuthenticationToken) {
            throw new AuthenticationCredentialsNotFoundException("User is not authenticated");
        }
        var user = (User) authentication.getPrincipal();
        return mapper.toDto(user);
    }

    @Override
    public void changePassword(Long id, ChangePasswordRequest request) {
        log.info("changePassword id {} password {}", id, request);
        var currentUser = findCurrentUser();
        if (!currentUser.admin() && !currentUser.id().equals(id)) {
            throw new AccessDeniedException("You are not allowed to change this password");
        }
        var user = find(id);
        var newPassword = request.newPassword();
        if (passwordEncoder.matches(newPassword, user.getPassword())) {
            throw new BusinessException("New password must be different from the current password");
        }
        user.setPassword(passwordEncoder.encode(newPassword));
        repository.save(user);
    }

    @Override
    public void delete(Long id) {
        log.info("delete id {}", id);
        var user = find(id);
        repository.delete(user);
    }

    private void populateUserRoles(User user, Set<Long> roleIds) {
        log.info("populateUserRoles roleIds {}", roleIds);
        var roles = roleIds.stream().map(roleService::find).collect(Collectors.toList());
        user.setRoles(roles);
    }

    private Optional<User> findByUsername(String username) {
        log.info("findByUsername {}", username);
        return repository.findByUsername(username);
    }

    private Optional<User> findByEmail(String email) {
        log.info("findByEmail email {}", email);
        return repository.findByEmail(email);
    }

    private void validateUqUsername(String username) {
        log.info("validateUqUsername {}", username);
        var optUser = findByUsername(username);
        if (optUser.isPresent()) {
            throw new DuplicateKeyException("This username is already taken");
        }
    }

    private void validateUqEmail(String email) {
        log.info("validateUqEmail email {}", email);
        var optUser = findByEmail(email);
        if (optUser.isPresent()) {
            throw new DuplicateKeyException("This email is already taken");
        }
    }
}
