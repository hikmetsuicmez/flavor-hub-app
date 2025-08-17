package com.hikmetsuicmez.FlavorHub.docs;

import com.hikmetsuicmez.FlavorHub.response.Response;
import com.hikmetsuicmez.FlavorHub.role.dtos.RoleDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Tag(name = "Role API", description = "Endpoints for role management")
public interface RoleApiDocs {

    @Operation(summary = "Create a new role", description = "Creates a new role with the provided details.")
    @ApiResponse(responseCode = "201", description = "Role created successfully", content = @Content(schema = @Schema(implementation = Response.class)))
    @ApiResponse(responseCode = "400", description = "Invalid input data", content = @Content(schema = @Schema(implementation = Response.class)))
    @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(schema = @Schema(implementation = Response.class)))
    ResponseEntity<Response<RoleDTO>> createRole(@Parameter RoleDTO roleDTO);

    @Operation(summary = "Update an existing role", description = "Updates the details of an existing role.")
    @ApiResponse(responseCode = "200", description = "Role updated successfully", content = @Content(schema = @Schema(implementation = Response.class)))
    @ApiResponse(responseCode = "400", description = "Invalid input data", content = @Content(schema = @Schema(implementation = Response.class)))
    @ApiResponse(responseCode = "404", description = "Role not found", content = @Content(schema = @Schema(implementation = Response.class)))
    @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(schema = @Schema(implementation = Response.class)))
    ResponseEntity<Response<RoleDTO>> updateRole(@Parameter RoleDTO roleDTO);

    @Operation(summary = "Get all roles", description = "Retrieves a list of all roles in the system.")
    @ApiResponse(responseCode = "200", description = "Roles retrieved successfully", content = @Content(schema = @Schema(implementation = Response.class)))
    @ApiResponse(responseCode = "404", description = "No roles found", content = @Content(schema = @Schema(implementation = Response.class)))
    @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(schema = @Schema(implementation = Response.class)))
    ResponseEntity<Response<List<RoleDTO>>> getAllRoles();

    @Operation(summary = "Delete a role", description = "Deletes a role by its ID.")
    @ApiResponse(responseCode = "204", description = "Role deleted successfully", content = @Content(schema = @Schema(implementation = Response.class)))
    @ApiResponse(responseCode = "404", description = "Role not found", content = @Content(schema = @Schema(implementation = Response.class)))
    @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(schema = @Schema(implementation = Response.class)))
    @Parameter(name = "roleId", description = "ID of the role to delete", required = true)
    ResponseEntity<Response<?>> deleteRole(@Parameter Long roleId);
}
