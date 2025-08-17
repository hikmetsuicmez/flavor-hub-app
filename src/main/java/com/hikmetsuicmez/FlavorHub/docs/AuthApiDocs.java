package com.hikmetsuicmez.FlavorHub.docs;

import com.hikmetsuicmez.FlavorHub.auth_users.dtos.LoginRequest;
import com.hikmetsuicmez.FlavorHub.auth_users.dtos.LoginResponse;
import com.hikmetsuicmez.FlavorHub.auth_users.dtos.RegistrationRequest;
import com.hikmetsuicmez.FlavorHub.response.Response;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;

@Tag(name = "Auth", description = "Authentication and User Management APIs")
public interface AuthApiDocs {

    @Operation(summary = "User Registration", description = "Register a new user with the provided details.")
    @ApiResponse(responseCode = "200", description = "User registered successfully.", content = @Content(schema = @Schema(implementation = Response.class)))
    @ApiResponse(responseCode = "400", description = "Invalid input data.", content = @Content(schema = @Schema(implementation = Response.class)))
    @ApiResponse(responseCode = "409", description = "User already exists.", content = @Content(schema = @Schema(implementation = Response.class)))
    @ApiResponse(responseCode = "500", description = "Internal server error.", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    ResponseEntity<Response<?>> register(@Parameter RegistrationRequest registrationRequest);

    @Operation(summary = "User Login", description = "Authenticate a user and return a JWT token.")
    @ApiResponse(responseCode = "200", description = "Login successful, returns JWT token.", content = @Content(schema = @Schema(implementation = Response.class)))
    @ApiResponse(responseCode = "400", description = "Invalid login credentials.", content = @Content(schema = @Schema(implementation = Response.class)))
    @ApiResponse(responseCode = "401", description = "Unauthorized access, invalid credentials.", content = @Content(schema = @Schema(implementation = Response.class)))
    @ApiResponse(responseCode = "500", description = "Internal server error.", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    ResponseEntity<Response<LoginResponse>> login(@Parameter LoginRequest loginRequest);
}
