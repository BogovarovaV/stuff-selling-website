package ru.skypro.homework.dto;

import lombok.Data;

@Data
public class CreateUser   {
  private String email = null;
  private String firstName = null;
  private String lastName = null;
  private String password = null;
  private String phone = null;
}
