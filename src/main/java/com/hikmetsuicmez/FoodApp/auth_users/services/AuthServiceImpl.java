package com.hikmetsuicmez.FoodApp.auth_users.services;

import com.hikmetsuicmez.FoodApp.auth_users.dtos.LoginRequest;
import com.hikmetsuicmez.FoodApp.auth_users.dtos.LoginResponse;
import com.hikmetsuicmez.FoodApp.auth_users.dtos.RegistrationRequest;
import com.hikmetsuicmez.FoodApp.auth_users.entity.User;
import com.hikmetsuicmez.FoodApp.auth_users.repository.UserRepository;
import com.hikmetsuicmez.FoodApp.exceptions.BadRequestException;
import com.hikmetsuicmez.FoodApp.exceptions.NotFoundException;
import com.hikmetsuicmez.FoodApp.response.Response;
import com.hikmetsuicmez.FoodApp.role.entity.Role;
import com.hikmetsuicmez.FoodApp.role.repository.RoleRepository;
import com.hikmetsuicmez.FoodApp.security.JwtUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthServiceImpl implements  AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;
    private final RoleRepository roleRepository;

    @Override
    public Response<?> register(RegistrationRequest registrationRequest) {
        log.info("INSIDE regiser()");

        if (userRepository.existsByEmail(registrationRequest.getEmail())) {
            throw new BadRequestException("Email already exists");
        }

        // collect all roles from the request
        List<Role> userRoles;

        if (registrationRequest.getRoles() != null && !registrationRequest.getRoles().isEmpty()) {
            userRoles = registrationRequest.getRoles()
                    .stream()
                    .map(roleName ->
                            roleRepository.findByName(roleName.toUpperCase())
                            .orElseThrow(() -> new NotFoundException("Role with name " + roleName + " not found")))
                    .toList();
        } else {
            // if no roles are provided, default to customer
            Role defaultRole = roleRepository.findByName("CUSTOMER")
                    .orElseThrow(() -> new NotFoundException("Default role CUSTOMER not found"));


            userRoles = List.of(defaultRole);
        }

        // build the user object
        User userToSave = User.builder()
                .name(registrationRequest.getName())
                .email(registrationRequest.getEmail())
                .phoneNumber(registrationRequest.getPhoneNumber())
                .address(registrationRequest.getAddress())
                .password(passwordEncoder.encode(registrationRequest.getPassword()))
                .roles(userRoles)
                .isActive(true)
                .createdAt(LocalDateTime.now())
                .build();

        // save the user
        userRepository.save(userToSave);

        log.info("user registered successfully: {}", userToSave.getEmail());

        return Response.builder()
                .statusCode(HttpStatus.OK.value())
                .message("User Registered Successfully")
                .build();

    }

    @Override
    public Response<LoginResponse> login(LoginRequest loginRequest) {

        log.info("INSIDE login()");

        User user = userRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new NotFoundException("User with email " + loginRequest.getEmail() + " not found"));

        if (!user.isActive()) {
            throw new NotFoundException("Account not active. Please contact customer support.");
        }

        // verify the password
        if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            throw new BadRequestException("Invalid Password");
        }

        // generate JWT token
        String token = jwtUtils.generateToken(user.getEmail());

        // Extract roles names as a list
        List<String> roleNames = user.getRoles()
                .stream()
                .map(Role::getName)
                .toList();

        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setToken(token);
        loginResponse.setRoles(roleNames);

        return Response.<LoginResponse>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Login Successful")
                .data(loginResponse)
                .build();
    }
}
