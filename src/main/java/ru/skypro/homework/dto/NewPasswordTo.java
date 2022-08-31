package ru.skypro.homework.dto;

import lombok.Data;

import javax.validation.constraints.Size;

@Data
public class NewPasswordTo {

    @Size(min = 8)
    private String currentPassword;

    @Size(min = 8)
    private String newPassword;
}
