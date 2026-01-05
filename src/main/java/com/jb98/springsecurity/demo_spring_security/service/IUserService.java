package com.jb98.springsecurity.demo_spring_security.service;

import com.jb98.springsecurity.demo_spring_security.controller.request.ChangePasswordRequest;
import com.jb98.springsecurity.demo_spring_security.controller.request.UserRequest;
import com.jb98.springsecurity.demo_spring_security.dto.UserDto;
import com.jb98.springsecurity.demo_spring_security.entity.User;
import com.jb98.springsecurity.demo_spring_security.exception.BusinessException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;

public interface IUserService {
    /**
     * Returns a paginated list of users.
     * @param pageable pagination information
     * @return page of UserDto
     */
    Page<UserDto> findAll(Pageable pageable);

    /**
     * Finds an user by its id.
     * @param id user identifier
     * @return User entity
     * @throws EntityNotFoundException if the user does not exist
     */
    User find(Long id);

    /**
     * Finds an user by its id.
     * @param id user identifier
     * @return User DTO
     * @throws EntityNotFoundException if the user does not exist
     */
    UserDto findDto(Long id);

    /**
     * Creates a new user.
     * @param request user data
     * @return created user DTO
     * @throws DuplicateKeyException if the username already exists
     */
    UserDto create(UserRequest request);

    /**
     * Updates an existing user
     * @param id user identifier
     * @param request updated data
     * @return updated user DTO
     * @throws DuplicateKeyException if the updated username already exists
     * @throws EntityNotFoundException if the user does not exist
     */
    UserDto update(Long id, UserRequest request);

    /**
     * Returns the current user authenticated
     * @return User DTO
     * @throws AuthenticationCredentialsNotFoundException if the user is not authenticated
     */
    UserDto findCurrentUser();

    /**
     * Updates the password of an existing user.
     * Only ADMIN users can update the password of any user.
     * @param id user identifier
     * @param request updated data
     * @throws AccessDeniedException if a non admin user changes the password of a different user.
     * @throws BusinessException if the user is not authenticated
     */
    void changePassword(Long id, ChangePasswordRequest request);

    /**
     * Deletes an user
     * @param id user identifier
     * @throws EntityNotFoundException if the user does not exist
     */
    void delete(Long id);
}
