package ru.skypro.homework.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.skypro.homework.dto.LoginReq;
import ru.skypro.homework.dto.RegisterReq;
import ru.skypro.homework.dto.Role;
import ru.skypro.homework.service.AuthService;

import javax.validation.Valid;

import static ru.skypro.homework.dto.Role.USER;

@Slf4j
@CrossOrigin(value = "http://localhost:3000")
@RestController
@RequiredArgsConstructor
@Validated
public class AuthController {

    private final AuthService authService;

    @Operation(
            tags = "Авторизация (AuthController)",
            summary = "Вход (login)"
    )
    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginReq req) {
        if (authService.login(req.getUsername(), req.getPassword())) {
            System.out.println("Login");
            return ResponseEntity.ok().build();
        } else {
            System.out.println("Login incorrect");
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }

    @Operation(
            tags = "Авторизация (AuthController)",
            summary = "Регистрация (register)"
    )
    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterReq req) {
        Role role = req.getRole() == null ? USER : req.getRole();
        if (authService.register(req, role)) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
}
