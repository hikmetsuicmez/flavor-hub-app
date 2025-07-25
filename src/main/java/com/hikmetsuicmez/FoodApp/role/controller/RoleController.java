package com.hikmetsuicmez.FoodApp.role.controller;

import com.hikmetsuicmez.FoodApp.response.Response;
import com.hikmetsuicmez.FoodApp.role.dtos.RoleDTO;
import com.hikmetsuicmez.FoodApp.role.services.RoleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/roles")
@RequiredArgsConstructor
@PreAuthorize("hasAuthority('ADMIN')")
public class RoleController {

    private final RoleService roleService;

    @PostMapping
    public ResponseEntity<Response<RoleDTO>> createRole(@RequestBody @Valid RoleDTO roleDTO) {
        Response<RoleDTO> response = roleService.createRole(roleDTO);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @PutMapping
    public ResponseEntity<Response<RoleDTO>> updateRole(@RequestBody @Valid RoleDTO roleDTO) {
        Response<RoleDTO> response = roleService.updateRole(roleDTO);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @GetMapping
    public ResponseEntity<Response<List<RoleDTO>>> getAllRoles() {
        Response<List<RoleDTO>> response = roleService.getAllRoles();
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @DeleteMapping("/{roleId}")
    public ResponseEntity<Response<?>> deleteRole(@PathVariable Long roleId) {
        Response<?> response = roleService.deleteRole(roleId);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

}
