package ru.skypro.homework.model;

import lombok.Data;

@Data
public class User {
    private int id;
    private String firstName;
    private String lastName;
    private String password;
    private String email;
    private String phone;
}
