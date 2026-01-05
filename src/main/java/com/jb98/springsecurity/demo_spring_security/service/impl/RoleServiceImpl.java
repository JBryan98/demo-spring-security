package com.jb98.springsecurity.demo_spring_security.service.impl;

import com.jb98.springsecurity.demo_spring_security.controller.mapper.RoleRequestMapper;
import com.jb98.springsecurity.demo_spring_security.controller.request.RoleRequest;
import com.jb98.springsecurity.demo_spring_security.dto.RoleDto;
import com.jb98.springsecurity.demo_spring_security.entity.Role;
import com.jb98.springsecurity.demo_spring_security.exception.BusinessException;
import com.jb98.springsecurity.demo_spring_security.mapper.RoleMapper;
import com.jb98.springsecurity.demo_spring_security.repository.RoleRepository;
import com.jb98.springsecurity.demo_spring_security.service.IRoleService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class RoleServiceImpl implements IRoleService {
    private final RoleRepository repository;
    private final RoleMapper mapper;
    private final RoleRequestMapper rqMapper;

    @Override
    @Transactional(readOnly = true)
    public Page<RoleDto> findAll(Pageable pageable) {
        log.info("findAll pageable {}", pageable);
        return repository.findAll(pageable).map(mapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Role find(Long id) {
        log.info("findById {}", id);
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Role not found with id '" + id + "'."));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Role> findByName(String name) {
        log.info("findByName name {}", name);
        return repository.findByName(name);
    }

    @Override
    @Transactional(readOnly = true)
    public RoleDto findDto(Long id) {
        log.info("findDtoById id {}", id);
        return mapper.toDto(find(id));
    }

    @Override
    public RoleDto create(RoleRequest request) {
        log.info("create request {}", request);
        validateUqName(request.name());
        var role = repository.save(rqMapper.toEntity(request));
        return mapper.toDto(role);
    }

    @Override
    public RoleDto update(Long id, RoleRequest request) {
        log.info("update id {} request {}", id, request);
        var role = find(id);
        if (!role.getName().equals(request.name())) {
            validateUqName(request.name());
        }
        rqMapper.update(request, role);
        repository.save(role);
        return mapper.toDto(role);
    }

    @Override
    public void delete(Long id) {
        log.info("delete id {}", id);
        var role = find(id);
        if (!role.getUsers().isEmpty()) {
            throw new BusinessException("Can't delete this role because it has one or more associated users.");
        }
        repository.delete(role);
    }

    private void validateUqName(String name){
        log.info("validateUqName name {}", name);
        var optRole = findByName(name);
        if (optRole.isPresent()){
            throw new DuplicateKeyException("The role with name '" + name + "' already exists.");
        }
    }
}
