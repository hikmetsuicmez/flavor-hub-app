package com.hikmetsuicmez.FlavorHub.auth_users.services;

import com.hikmetsuicmez.FlavorHub.auth_users.dtos.UserDTO;
import com.hikmetsuicmez.FlavorHub.auth_users.entity.User;
import com.hikmetsuicmez.FlavorHub.response.Response;

import java.util.List;

public interface UserService {

    User getCurrentLoggedInUser();
    Response<List<UserDTO>> getAllUsers();
    Response<UserDTO> GetOwnAccountDetails();
    Response<?> updateOwnAccount(UserDTO userDTO);
    Response<?> deactivateOwnAccount();
}
