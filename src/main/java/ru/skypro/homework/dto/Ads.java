package ru.skypro.homework.dto;

import lombok.Data;
import org.hibernate.annotations.Type;

import javax.persistence.Lob;

@Data
public class Ads {
  private Integer author;
//  private String imagePath;
  private Integer pk;
  private Integer price;
  private String title;
}
