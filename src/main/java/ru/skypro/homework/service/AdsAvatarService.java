package ru.skypro.homework.service;

import org.springframework.web.multipart.MultipartFile;

public interface AdsAvatarService {

    String saveAds(MultipartFile image);

    byte [] getAdsAvatar(String id);

}
