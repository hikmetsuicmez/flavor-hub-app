package com.hikmetsuicmez.FlavorHub.docs;

import com.hikmetsuicmez.FlavorHub.auth_users.dtos.UserDTO;
import com.hikmetsuicmez.FlavorHub.response.Response;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Tag(name = "User API", description = "Endpoints for user management and account operations")
public interface UserApiDocs {


    @Operation(summary = "Get User Details", description = "Retrieve details of a specific user by their ID")
    @ApiResponse(responseCode = "200", description = "User details retrieved successfully", content = @Content(schema = @Schema(implementation = Response.class)))
    @ApiResponse(responseCode = "404", description = "User not found", content = @Content(schema = @Schema(implementation = Response.class)))
    @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(schema = @Schema(implementation = Response.class)))
    ResponseEntity<Response<List<UserDTO>>> getAllUsers();

    @Operation(summary = "Get Own Account Details", description = "Retrieve details of the currently authenticated user's account")
    @ApiResponse(responseCode = "200", description = "Own account details retrieved successfully", content = @Content(schema = @Schema(implementation = Response.class)))
    @ApiResponse(responseCode = "404", description = "User not found", content = @Content(schema = @Schema(implementation = Response.class)))
    @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(schema = @Schema(implementation = Response.class)))
    ResponseEntity<Response<?>> updateUser(
            @Parameter UserDTO userDTO,
            @Parameter MultipartFile imageFile);

    @Operation(summary = "Deactivate Own Account", description = "Deactivate the currently authenticated user's account")
    @ApiResponse(responseCode = "200", description = "Account deactivated successfully", content = @Content(schema = @Schema(implementation = Response.class)))
    @ApiResponse(responseCode = "404", description = "User not found", content = @Content(schema = @Schema(implementation = Response.class)))
    @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(schema = @Schema(implementation = Response.class)))
    ResponseEntity<Response<?>> deactivateOwnAccount();

    @Operation(summary = "Get Own Account Details", description = "Retrieve details of the currently authenticated user's account")
    @ApiResponse(responseCode = "200", description = "Own account details retrieved successfully", content = @Content(schema = @Schema(implementation = Response.class)))
    @ApiResponse(responseCode = "404", description = "User not found", content = @Content(schema = @Schema(implementation = Response.class)))
    @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(schema = @Schema(implementation = Response.class)))
    ResponseEntity<Response<UserDTO>> getOwnAccountDetails();
}
