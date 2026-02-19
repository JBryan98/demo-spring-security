package com.jb98.springsecurity.demo_spring_security.controller;

import com.jb98.springsecurity.demo_spring_security.controller.request.ChangePasswordRequest;
import com.jb98.springsecurity.demo_spring_security.controller.request.UserRequest;
import com.jb98.springsecurity.demo_spring_security.dto.UserDto;
import com.jb98.springsecurity.demo_spring_security.service.IUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@Tag(
        name = "User",
        description = "API for user management. Access is restricted based on user roles (ADMIN and USER)."
)
@RequiredArgsConstructor
public class UserController {
    private final IUserService service;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping
    @Operation(summary = "Get a list of paginated users.")
    public ResponseEntity<Page<UserDto>> findAll(Pageable pageable) {
        return ResponseEntity.ok(service.findAll(pageable));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN') or #id == authentication.principal.id")
    @Operation(summary = "Get a user by its ID.")
    public ResponseEntity<UserDto> findById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findDto(id));
    }

    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Operation(summary = "Create a new user.")
    public ResponseEntity<UserDto> create(@RequestBody @Valid UserRequest request) {
        return new ResponseEntity<>(service.create(request), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN') or #id == authentication.principal.id")
    @Operation(summary = "Update an existing user by its ID.")
    public ResponseEntity<UserDto> update(@PathVariable Long id, @RequestBody @Valid UserRequest request) {
        return ResponseEntity.ok(service.update(id, request));
    }

    @PatchMapping("/{id}/change-password")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER') or #id == authentication.principal.id")
    @Operation(summary = "Change the password of an existing user by its ID.")
    public ResponseEntity<Void> changePassword(@PathVariable Long id, @RequestBody @Valid ChangePasswordRequest request) {
        service.changePassword(id, request);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Operation(summary = "Delete aN user by its ID.")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.ok().build();
    }
}
