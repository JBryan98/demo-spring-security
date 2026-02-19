package com.jb98.springsecurity.demo_spring_security.controller;

import com.jb98.springsecurity.demo_spring_security.controller.request.RoleRequest;
import com.jb98.springsecurity.demo_spring_security.dto.RoleDto;
import com.jb98.springsecurity.demo_spring_security.service.IRoleService;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/roles")
@PreAuthorize("hasRole('ROLE_ADMIN')")
@Tag(
        name = "Role",
        description = "API for role management. All operations are restricted to users with the ADMIN role."
)
@RequiredArgsConstructor
public class RoleController {
    private final IRoleService service;

    @GetMapping
    @Operation(summary = "Get a list of paginated roles.")
    public ResponseEntity<Page<RoleDto>> findAll(Pageable pageable){
        return ResponseEntity.ok(service.findAll(pageable));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a role by its ID.")
    public ResponseEntity<RoleDto> findById(@PathVariable Long id){
        return ResponseEntity.ok(service.findDto(id));
    }

    @PostMapping
    @Operation(summary = "Create a new role.")
    public ResponseEntity<RoleDto> create(@RequestBody @Valid RoleRequest request){
        return new ResponseEntity<>(service.create(request), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an existing role by its ID.")
    public ResponseEntity<RoleDto> update(@PathVariable Long id, @RequestBody @Valid RoleRequest request){
        return new ResponseEntity<>(service.update(id, request), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a role by its ID.")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        service.delete(id);
        return ResponseEntity.ok().build();
    }
}
