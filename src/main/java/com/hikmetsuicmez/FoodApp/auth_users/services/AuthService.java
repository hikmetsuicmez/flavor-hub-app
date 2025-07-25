package com.hikmetsuicmez.FoodApp.auth_users.services;

import com.hikmetsuicmez.FoodApp.auth_users.dtos.LoginRequest;
import com.hikmetsuicmez.FoodApp.auth_users.dtos.LoginResponse;
import com.hikmetsuicmez.FoodApp.auth_users.dtos.RegistrationRequest;
import com.hikmetsuicmez.FoodApp.response.Response;

public interface AuthService {

    Response<?> register(RegistrationRequest registrationRequest);
    Response<LoginResponse> login(LoginRequest loginRequest);

}
