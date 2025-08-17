package com.hikmetsuicmez.FlavorHub.role.services;

import com.hikmetsuicmez.FlavorHub.response.Response;
import com.hikmetsuicmez.FlavorHub.role.dtos.RoleDTO;

import java.util.List;

public interface RoleService {

    Response<RoleDTO> createRole(RoleDTO roleDTO);
    Response<RoleDTO> updateRole(RoleDTO roleDTO);
    Response<List<RoleDTO>> getAllRoles();
    Response<?> deleteRole(Long roleId);
}
