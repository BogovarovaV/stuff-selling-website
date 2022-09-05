package ru.skypro.homework.dto;

import lombok.Data;

import javax.validation.constraints.*;

@Data
public class UserTo {

    private int id;

    @Size(min = 3)
    @NotBlank
    private String firstName;

    @Size(min = 3)
    @NotBlank
    private String lastName;

    @Email
    private String email;

    @Pattern(regexp = "^(\\+7|7|8)?[\\s\\-]?\\(?[489][0-9]{2}\\)?[\\s\\-]?[0-9]{3}[\\s\\-]?[0-9]{2}[\\s\\-]?[0-9]{2}$")
    private String phone;
}
