package ru.skypro.homework.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

@Data
public class Ads {
  @Positive
  private Integer author;
  @NotBlank
  private String image;
  @Positive
  private Integer pk;
  @Positive
  private Integer price;
  @Size(min = 8)
  @NotBlank
  private String title;
  @Size(min = 8)
  @NotBlank
  private String description;
}
