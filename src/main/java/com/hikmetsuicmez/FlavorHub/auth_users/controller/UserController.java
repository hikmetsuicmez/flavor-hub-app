package com.hikmetsuicmez.FlavorHub.auth_users.controller;

import com.hikmetsuicmez.FlavorHub.auth_users.dtos.UserDTO;
import com.hikmetsuicmez.FlavorHub.auth_users.services.UserService;
import com.hikmetsuicmez.FlavorHub.docs.UserApiDocs;
import com.hikmetsuicmez.FlavorHub.response.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController implements UserApiDocs {

    private final UserService userService;

    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN')")  // Only admin can access this endpoint
    public ResponseEntity<Response<List<UserDTO>>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @PutMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Response<?>> updateUser(
            @ModelAttribute UserDTO userDTO,
            @RequestPart(value = "imageFile", required = false) MultipartFile imageFile) {
        userDTO.setImageFile(imageFile);
        return ResponseEntity.ok(userService.updateOwnAccount(userDTO));
    }

    @DeleteMapping
    public ResponseEntity<Response<?>> deactivateOwnAccount() {
        return ResponseEntity.ok(userService.deactivateOwnAccount());
    }

    @GetMapping("/account")
    public ResponseEntity<Response<UserDTO>> getOwnAccountDetails() {
        return ResponseEntity.ok(userService.GetOwnAccountDetails());
    }

}
