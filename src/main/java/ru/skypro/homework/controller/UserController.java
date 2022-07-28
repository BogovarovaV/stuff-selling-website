package ru.skypro.homework.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.skypro.homework.dto.NewPasswordDto;
import ru.skypro.homework.dto.UserDto;
import ru.skypro.homework.model.User;

import java.util.Collection;

@RestController
@RequestMapping("/users")
public class UserController {

    @Operation(
            tags = "Пользователи (UserController)",
            summary = "Добавление пользователя (addUser)"
    )
    @PostMapping
    public ResponseEntity<User> addUser(@RequestBody User user) {
        return ResponseEntity.ok(user);
    }

    @Operation(
            tags = "Пользователи (UserController)",
            summary = "Получение пользователей (getUsers)"
    )
    @GetMapping("/me")
    public ResponseEntity<Collection<UserDto>> getUsers() {
        return ResponseEntity.ok(null);
    }

    @Operation(
            tags = "Пользователи (UserController)",
            summary = "Редактирование пользователя (updateUser)"
    )
    @PatchMapping("/me")
    public ResponseEntity<UserDto> updateUser(@RequestBody UserDto user) {
        return ResponseEntity.ok(user);
    }

    @Operation(
            tags = "Пользователи (UserController)",
            summary = "Изменение пароля (setPassword)"
    )
    @PostMapping("set_password")
    public ResponseEntity<NewPasswordDto> setPassword(@RequestBody NewPasswordDto newPassword) {
        return ResponseEntity.ok(newPassword);
    }

    @Operation(
            tags = "Пользователи (UserController)",
            summary = "Получение пользователя по id (getUser)"
    )
    @GetMapping("{id}")
    public ResponseEntity<UserDto> getUser(@PathVariable("id") int id) {
        return ResponseEntity.ok(new UserDto());
    }
}
