package ru.skypro.homework.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class CreateAds   {

  private MultipartFile image;
  private String description;
  private Integer price;
  private String title;
}
