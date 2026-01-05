package com.jb98.springsecurity.demo_spring_security.service;

import com.jb98.springsecurity.demo_spring_security.controller.request.RoleRequest;
import com.jb98.springsecurity.demo_spring_security.dto.RoleDto;
import com.jb98.springsecurity.demo_spring_security.entity.Role;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface IRoleService {
    /**
     * Returns a paginated list of roles.
     * @param pageable pagination information
     * @return page of RoleDto
     */
    Page<RoleDto> findAll(Pageable pageable);

    /**
     * Finds a role by its id.
     * @param id role identifier
     * @return Role entity
     * @throws EntityNotFoundException if the role does not exist
     */
    Role find(Long id);

    /**
     * Finds a role by its name.
     * @param name role name
     * @return optional role
     */
    Optional<Role> findByName(String name);

    /**
     * Finds a role by its id.
     * @param id role identifier
     * @return Role DTO
     * @throws EntityNotFoundException if the role does not exist
     */
    RoleDto findDto(Long id);

    /**
     * Creates a new role.
     * @param request role data
     * @return created role DTO
     * @throws DuplicateKeyException if the role name already exists
     */
    RoleDto create(RoleRequest request);

    /**
     * Updates an existing role.
     * @param id role identifier
     * @param request updated data
     * @return updated role DTO
     * @throws DuplicateKeyException if the role name already exists
     * @throws EntityNotFoundException if the role does not exist
     *
     */
    RoleDto update(Long id, RoleRequest request);

    /**
     * Deletes a role if it has no users assigned.
     * @param id role identifier
     * @throws IllegalArgumentException if the role has users
     */
    void delete(Long id);
}
