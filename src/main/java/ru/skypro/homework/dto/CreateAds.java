package ru.skypro.homework.dto;

import lombok.Data;

@Data
public class CreateAds   {

//  private MultipartFile image;
  private String imagePath;
  private String description;
  private Integer price;
  private String title;
}
