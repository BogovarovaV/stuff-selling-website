package ru.skypro.homework.dto;

import lombok.Data;

import java.util.List;

@Data
public class ResponseWrapperUserTo {
  private Integer count;
  private List<UserTo> results;
}
