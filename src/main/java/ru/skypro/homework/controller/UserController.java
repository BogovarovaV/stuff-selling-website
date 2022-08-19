package ru.skypro.homework.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.skypro.homework.dto.NewPassword;
import ru.skypro.homework.dto.ResponseWrapperUser;
import ru.skypro.homework.dto.User;
import ru.skypro.homework.service.UserService;


@CrossOrigin(value = "http://localhost:3000")
@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Operation(
            tags = "Пользователи (UserController)",
            summary = "Получение пользователей (getUsers)"
    )
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/me")
    public ResponseEntity<ResponseWrapperUser> getUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @Operation(
            tags = "Пользователи (UserController)",
            summary = "Редактирование пользователя (updateUser)"
    )
//    @PreAuthorize("hasRole('ADMIN') or #user == authentication.principal.username")
    @PatchMapping("/me")
    public ResponseEntity<User> updateUser(@RequestBody User user) {
        System.out.println("Updating user");
        return ResponseEntity.ok(userService.updateUser(user));
    }

    @Operation(
            tags = "Пользователи (UserController)",
            summary = "Изменение пароля (setPassword)"
    )
    @PreAuthorize("#username == authentication.principal.username")
    @PostMapping("set_password")
    public ResponseEntity<NewPassword> setPassword(@RequestBody NewPassword newPassword) {
        return ResponseEntity.ok(newPassword);
    }

    @Operation(
            tags = "Пользователи (UserController)",
            summary = "Получение пользователя по id (getUser)"
    )
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("{id}")
    public ResponseEntity<User> getUser(@PathVariable("id") int id) {
        return ResponseEntity.ok(userService.getUser(id));
    }
}
