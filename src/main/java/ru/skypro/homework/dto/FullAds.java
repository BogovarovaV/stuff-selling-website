package ru.skypro.homework.dto;

import lombok.Data;
import org.hibernate.annotations.Type;

import javax.persistence.Lob;

@Data
public class FullAds   {
  private String authorFirstName;
  private String authorLastName;
  private String description;
  private String email;
  @Lob
  @Type(type = "org.hibernate.type.ImageType")
  private byte [] image;
  private String phone;
  private Integer pk;
  private Integer price;
  private String title;
}
