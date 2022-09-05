package ru.skypro.homework.dto;

import lombok.Data;

import java.util.List;

@Data
public class ResponseWrapperAdsTo {
  private Integer count;
  private List<AdsTo> results;
}
