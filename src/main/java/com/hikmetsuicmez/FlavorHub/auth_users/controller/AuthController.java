package com.hikmetsuicmez.FlavorHub.auth_users.controller;

import com.hikmetsuicmez.FlavorHub.auth_users.dtos.LoginRequest;
import com.hikmetsuicmez.FlavorHub.auth_users.dtos.LoginResponse;
import com.hikmetsuicmez.FlavorHub.auth_users.dtos.RegistrationRequest;
import com.hikmetsuicmez.FlavorHub.auth_users.services.AuthService;
import com.hikmetsuicmez.FlavorHub.contants.ApiEndpoints;
import com.hikmetsuicmez.FlavorHub.docs.AuthApiDocs;
import com.hikmetsuicmez.FlavorHub.response.Response;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(ApiEndpoints.Auth.BASE)
@RequiredArgsConstructor
public class AuthController implements AuthApiDocs {

    private final AuthService authService;

    @PostMapping(ApiEndpoints.Auth.REGISTER)
    public ResponseEntity<Response<?>> register(@RequestBody @Valid RegistrationRequest registrationRequest) {
        return ResponseEntity.ok(authService.register(registrationRequest));
    }

    @PostMapping(ApiEndpoints.Auth.LOGIN)
    public ResponseEntity<Response<LoginResponse>> login(@RequestBody @Valid LoginRequest loginRequest) {
        return ResponseEntity.ok(authService.login(loginRequest));
    }
}
