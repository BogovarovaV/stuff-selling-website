package ru.skypro.homework.dto;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
public class RegisterReq {
    @Email
    private String username;
    @Size(min = 8)
    private String password;
    @NotBlank
    @Size(min = 3)
    private String firstName;
    @NotBlank
    @Size(min = 3)
    private String lastName;
    @Pattern(regexp = "^(\\+7|7|8)?[\\s\\-]?\\(?[489][0-9]{2}\\)?[\\s\\-]?[0-9]{3}[\\s\\-]?[0-9]{2}[\\s\\-]?[0-9]{2}$")
    private String phone;
    private Role role;
}
