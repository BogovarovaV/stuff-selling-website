package ru.skypro.homework.service;

import org.springframework.web.multipart.MultipartFile;

public interface AdsAvatarService {

    Integer saveAds(MultipartFile file);

    byte [] getAdsAvatar(Integer id);

}
