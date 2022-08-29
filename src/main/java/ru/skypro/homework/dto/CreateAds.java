package ru.skypro.homework.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

@Data
public class CreateAds   {

  @NotBlank
  private String description;
  @Positive
  private Integer price;
  @NotBlank
  private String title;
}
