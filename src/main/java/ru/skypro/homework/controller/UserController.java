package ru.skypro.homework.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.skypro.homework.dto.CreateUser;
import ru.skypro.homework.dto.NewPassword;
import ru.skypro.homework.dto.ResponseWrapperUser;
import ru.skypro.homework.dto.User;


@CrossOrigin(value = "http://localhost:3000")
@RestController
@RequestMapping("/users")
public class UserController {

    @Operation(
            tags = "Пользователи (UserController)",
            summary = "Добавление пользователя (addUser)"
    )
    @PostMapping
    public ResponseEntity<CreateUser> addUser(@RequestBody CreateUser user) {
        return ResponseEntity.ok(user);
    }

    @Operation(
            tags = "Пользователи (UserController)",
            summary = "Получение пользователей (getUsers)"
    )
    @GetMapping("/me")
    public ResponseEntity<ResponseWrapperUser> getUsers() {
        return ResponseEntity.ok(new ResponseWrapperUser());
    }

    @Operation(
            tags = "Пользователи (UserController)",
            summary = "Редактирование пользователя (updateUser)"
    )
    @PatchMapping("/me")
    public ResponseEntity<User> updateUser(@RequestBody User user) {
        return ResponseEntity.ok(user);
    }

    @Operation(
            tags = "Пользователи (UserController)",
            summary = "Изменение пароля (setPassword)"
    )
    @PostMapping("set_password")
    public ResponseEntity<NewPassword> setPassword(@RequestBody NewPassword newPassword) {
        return ResponseEntity.ok(newPassword);
    }

    @Operation(
            tags = "Пользователи (UserController)",
            summary = "Получение пользователя по id (getUser)"
    )
    @GetMapping("{id}")
    public ResponseEntity<User> getUser(@PathVariable("id") int id) {
        return ResponseEntity.ok(new User());
    }
}
