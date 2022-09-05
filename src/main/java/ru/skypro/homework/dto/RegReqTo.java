package ru.skypro.homework.dto;

import lombok.Data;

@Data
public class RegReqTo {
  private String password;

  public enum RoleEnum {
    ADMIN("ADMIN"),
    USER("USER");

    RoleEnum(String value) {
    }
  }
  private RoleEnum role;
  private String username;
}
