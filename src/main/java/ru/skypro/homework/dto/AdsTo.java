package ru.skypro.homework.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

@Data
public class AdsTo {
  @Positive
  private Integer author;

  private String image;
  @Positive
  private Integer pk;
  @Positive
  private Integer price;
  @NotBlank
  @Size(min = 3)
  private String title;
  @Size(min = 8)
  @NotBlank
  private String description;
}
