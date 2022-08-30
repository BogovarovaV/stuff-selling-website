package ru.skypro.homework.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.time.OffsetDateTime;
@Data
public class AdsCommentTo {
  @Positive
  private Integer author;
  @PastOrPresent
  private OffsetDateTime createdAt;
  @Positive
  private Integer pk;
  @NotBlank
  @Size(min = 8)
  private String text;

}
