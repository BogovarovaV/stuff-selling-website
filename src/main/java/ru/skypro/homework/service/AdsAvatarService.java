package ru.skypro.homework.service;

import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.CreateAds;
import ru.skypro.homework.model.Advert;

public interface AdsAvatarService {

    String saveAds(MultipartFile image);

    byte [] getAdsAvatar(String id);

}
