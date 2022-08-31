package ru.skypro.homework.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.skypro.homework.dto.NewPasswordTo;
import ru.skypro.homework.dto.ResponseWrapperUserTo;
import ru.skypro.homework.dto.UserTo;
import ru.skypro.homework.model.User;
import ru.skypro.homework.service.AuthService;
import ru.skypro.homework.service.UserService;

import javax.validation.Valid;
import javax.validation.constraints.Positive;


@CrossOrigin(value = "http://localhost:3000")
@RestController
@RequestMapping("/users")
@Validated
public class UserController {

    private final UserService userService;

    private final AuthService authService;

    public UserController(UserService userService, AuthService authService) {
        this.userService = userService;
        this.authService = authService;
    }

    @Operation(
            tags = "Пользователи (UserController)",
            summary = "Получение пользователей (getUsers)"
    )
    @GetMapping("/me")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
    public ResponseEntity<User> getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.getUserByUsername(authentication.getName());
        return ResponseEntity.ok(user);
    }

    @Operation(
            tags = "Пользователи (UserController)",
            summary = "Редактирование пользователя (updateUser)"
    )
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
    @PatchMapping("/me")
    public ResponseEntity<UserTo> updateUser(@Valid @RequestBody UserTo userTo) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return ResponseEntity.ok(userService.updateUser(userTo, authentication));
    }

    @Operation(
            tags = "Пользователи (UserController)",
            summary = "Изменение пароля (setPassword)"
    )
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
    @PostMapping("set_password")
    public ResponseEntity<NewPasswordTo> setPassword(@Valid @RequestBody NewPasswordTo newPasswordTo) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        authService.changePassword(
                authentication.getName(),
                newPasswordTo.getCurrentPassword(),
                newPasswordTo.getNewPassword()
        );
        return ResponseEntity.ok(newPasswordTo);
    }

    @Operation(
            tags = "Пользователи (UserController)",
            summary = "Получение пользователя по id (getUser)"
    )
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("{id}")
    public ResponseEntity<UserTo> getAnyUser(@Positive @PathVariable("id") int id) {
        return ResponseEntity.ok(userService.getUser(id));
    }

    @Operation(
            tags = "Пользователи (UserController)",
            summary = "Получение всех пользователей с сортировкой по id (getAllUsersWithOrderById)"
    )
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/")
    public ResponseEntity<ResponseWrapperUserTo> getAllUsersWithOrderById() {
        return ResponseEntity.ok(userService.getAllUsersWithOrderById());
    }
}
