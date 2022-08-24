package ru.skypro.homework.dto;

import lombok.Data;
import org.hibernate.annotations.Type;

import javax.persistence.Lob;

@Data
public class Ads {
  private Integer author;
  @Lob
  @Type(type = "org.hibernate.type.ImageType")
  private byte [] image;
  private Integer pk;
  private Integer price;
  private String title;
}
