package com.hikmetsuicmez.FlavorHub.auth_users.services;

import com.hikmetsuicmez.FlavorHub.auth_users.dtos.LoginRequest;
import com.hikmetsuicmez.FlavorHub.auth_users.dtos.LoginResponse;
import com.hikmetsuicmez.FlavorHub.auth_users.dtos.RegistrationRequest;
import com.hikmetsuicmez.FlavorHub.response.Response;

public interface AuthService {

    Response<?> register(RegistrationRequest registrationRequest);
    Response<LoginResponse> login(LoginRequest loginRequest);

}
