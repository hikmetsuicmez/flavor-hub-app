package com.hikmetsuicmez.FoodApp.role.services;

import com.hikmetsuicmez.FoodApp.response.Response;
import com.hikmetsuicmez.FoodApp.role.dtos.RoleDTO;

import java.util.List;

public interface RoleService {

    Response<RoleDTO> createRole(RoleDTO roleDTO);
    Response<RoleDTO> updateRole(RoleDTO roleDTO);
    Response<List<RoleDTO>> getAllRoles();
    Response<?> deleteRole(Long roleId);
}
