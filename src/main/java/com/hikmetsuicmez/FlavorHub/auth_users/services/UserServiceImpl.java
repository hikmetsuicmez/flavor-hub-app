package com.hikmetsuicmez.FlavorHub.auth_users.services;

import com.hikmetsuicmez.FlavorHub.auth_users.dtos.UserDTO;
import com.hikmetsuicmez.FlavorHub.auth_users.entity.User;
import com.hikmetsuicmez.FlavorHub.auth_users.repository.UserRepository;
import com.hikmetsuicmez.FlavorHub.aws.AWSS3Service;
import com.hikmetsuicmez.FlavorHub.email_notification.dtos.NotificationDTO;
import com.hikmetsuicmez.FlavorHub.email_notification.services.NotificationService;
import com.hikmetsuicmez.FlavorHub.exceptions.BadRequestException;
import com.hikmetsuicmez.FlavorHub.exceptions.NotFoundException;
import com.hikmetsuicmez.FlavorHub.response.Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.net.URL;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;
    private final NotificationService notificationService;
    private final AWSS3Service awsS3Service;

    @Override
    public User getCurrentLoggedInUser() {

        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("User not found"));

    }

    @Override
    public Response<List<UserDTO>> getAllUsers() {

        log.info("INSIDE getAllUsers()");
        List<User> userList = userRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));
        List<UserDTO> userDTOS = modelMapper.map(userList, new TypeToken<List<UserDTO>>() {}.getType());

        return Response.<List<UserDTO>>builder()
                .statusCode(HttpStatus.OK.value())
                .message("All users retrieved successfully")
                .data(userDTOS)
                .build();

    }

    @Override
    public Response<UserDTO> GetOwnAccountDetails() {

        log.info("INSIDE GetOwnAccountDetails()");
        User user = getCurrentLoggedInUser();
        UserDTO userDTO = modelMapper.map(user, UserDTO.class);

        return Response.<UserDTO>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Own account details retrieved successfully")
                .data(userDTO)
                .build();

    }

    @Override
    public Response<?> updateOwnAccount(UserDTO userDTO) {
        log.info("INSIDE updateOwnAccount()");

        // fetch current logged in user
        User user = getCurrentLoggedInUser();

        String profileUrl = user.getProfileUrl();
        MultipartFile imageFile = userDTO.getImageFile();

        // check if new imagefile was provided

        if (imageFile != null && !imageFile.isEmpty()) {
            // delete old image in cloud if it exists
            if (profileUrl != null && !profileUrl.isEmpty()) {
                String keyName = profileUrl.substring(profileUrl.lastIndexOf("/") + 1);
                awsS3Service.deleteFile("profile/" + keyName);
                log.info("Deleted old profile image from s3" );
            }
            String imageName = UUID.randomUUID().toString() + "_" + imageFile.getOriginalFilename();
            URL newImageUrl = awsS3Service.uploadFile("profile/" + imageName, imageFile);
            user.setProfileUrl(newImageUrl.toString());
        }

        // update user details

        if (userDTO.getName() != null) user.setName(userDTO.getName());
        if (userDTO.getSurname() != null) user.setSurname(userDTO.getSurname());
        if (userDTO.getPhoneNumber() != null) user.setPhoneNumber(userDTO.getPhoneNumber());
        if (userDTO.getAddress() != null) user.setAddress(userDTO.getAddress());


        if (userDTO.getEmail() != null && !userDTO.getEmail().equals(user.getEmail())) {
            // Var olan email ile yeni emaili karşılaştır
            if (userRepository.existsByEmail(userDTO.getEmail())) {
                throw new BadRequestException("Email already exists");
            }
            user.setEmail(userDTO.getEmail());
        }

        if (userDTO.getPassword() != null) user.setPassword(passwordEncoder.encode(userDTO.getPassword()));


        // save the user
        userRepository.save(user);


        return Response.builder()
                .statusCode(HttpStatus.OK.value())
                .message("Account updated successfully")
                .build();
    }

    @Override
    public Response<?> deactivateOwnAccount() {
        log.info("INSIDE deactivateOwnAccount()");

        User user = getCurrentLoggedInUser();

        user.setActive(false);
        userRepository.save(user);

        // SEND EMAIL AFTER DEACTIVATION
        NotificationDTO notificationDTO = NotificationDTO.builder()
                .recipient(user.getEmail())
                .subject("Account Deactivation")
                .body("Your account has been deactivated successfully. If this was a mistake, please contact support.")
                .build();

        notificationService.sendEmail(notificationDTO);

        return Response.builder()
                .statusCode(HttpStatus.OK.value())
                .message("Account deactivated successfully")
                .build();
    }
}
