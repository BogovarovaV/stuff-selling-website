package ru.skypro.homework.service.impl;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import ru.skypro.homework.dto.RegisterReq;
import ru.skypro.homework.dto.Role;
import ru.skypro.homework.exception.NoAccessException;
import ru.skypro.homework.model.User;
import ru.skypro.homework.service.AuthService;
import ru.skypro.homework.service.UserService;

import javax.validation.Valid;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserDetailsManager manager;

    private final PasswordEncoder encoder;

    private final UserService userService;

    public AuthServiceImpl(UserDetailsManager manager, UserService userService) {
        this.manager = manager;
        this.userService = userService;
        this.encoder = new BCryptPasswordEncoder();
    }

    /**
     * Account login by username and password
     * @param userName - username from client
     * @param password - password from client
     * @return boolean result of login
     */
    @Override
    public boolean login(String userName, String password) {
        if (!manager.userExists(userName)) {
            return false;
        }
        UserDetails userDetails = manager.loadUserByUsername(userName);
        String encryptedPassword = userDetails.getPassword();
        String encryptedPasswordWithoutEncryptionType = encryptedPassword.substring(8);
        return encoder.matches(password, encryptedPasswordWithoutEncryptionType);
    }

    /**
     * New user registration
     * @param registerReq - new user information as RegisterReq (DTO) from client
     * @param role - user role
     * @return boolean result of registration
     */
    @Override
    public boolean register(RegisterReq registerReq, Role role) {
        if (manager.userExists(registerReq.getUsername())) {
            return false;
        }
        manager.createUser(
                org.springframework.security.core.userdetails.User.withDefaultPasswordEncoder()
                        .password(registerReq.getPassword())
                        .username(registerReq.getUsername())
                        .roles(role.name())
                        .build()
        );
        User savedUser = this.userService.getUserByUsername(registerReq.getUsername());
        savedUser.setFirstName(registerReq.getFirstName());
        savedUser.setLastName(registerReq.getLastName());
        savedUser.setPhone(registerReq.getPhone());
        savedUser.setEmail(registerReq.getUsername());
        this.userService.updateUser(savedUser);
        return true;
    }

    /**
     * Change user password with indicating current password
     * @param username - username
     * @param currentPassword - user current password
     * @param newPassword - user new password or throw exception
     */
    @Override
    public void changePassword(String username, String currentPassword, String newPassword) {
        UserDetails userDetails = manager.loadUserByUsername(username);
        String encryptedPassword = userDetails.getPassword();
        String encryptedPasswordWithoutEncryptionType = encryptedPassword.substring(8);
        if (encoder.matches(currentPassword, encryptedPasswordWithoutEncryptionType)) {
            userService.savePassword(username, encoder.encode(newPassword));
        } else {
            throw new NoAccessException();
        }
    }
}
